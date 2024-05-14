package com.example.cinemaressys.services.booking;

import com.example.cinemaressys.dtos.booking.*;
import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.entities.*;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.*;
import com.example.cinemaressys.security.JwtTokenProvider;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepositories bookingRepositories;
    private final UserRepositories userRepositories;
    private final DictBookingStatusRepositories dictBookingStatusRepositories;
    private final MovieSessionRepositories movieSessionRepositories;
    private final SeatRepositories seatRepositories;
    private final PriceRepositories priceRepositories;
    private final BookingSeatRepositories bookingSeatRepositories;

    @Override
    public BookingResponseDto createNewBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto) {
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(bookingAddBookingRequestDto.getToken());
        User user = userRepositories.findUserByEmail(jwtClaims.getEmail());
        if (user == null) {
            throw new MyException("User not found!");
        }
        DictBookingStatus dictBookingStatus = dictBookingStatusRepositories.
                findByDictBookingStatusId(bookingAddBookingRequestDto.getBookingStatus());
        if (dictBookingStatus == null) {
            throw new MyException("Booking status not found!");
        }
        Integer bookingNumber = bookingRepositories.findMaxBookingNumber();
        if (bookingNumber == null) {
            bookingNumber = 10000000;
        }
        else {
            bookingNumber += 1;
        }
        Booking booking = new Booking(
                user,
                dictBookingStatus,
                bookingNumber,
                bookingAddBookingRequestDto.getTotalPrice()
        );
        List<BookingSeat> bookingSeatList = new ArrayList<>();

        for (BookingMovieSessionDto bookingMovieSessionDto :
                bookingAddBookingRequestDto.getBookingMovieSessionDtoList()) {
            MovieSession movieSession = movieSessionRepositories.
                    findByMovieSessionId(bookingMovieSessionDto.getMovieSessionId());

            List<Integer> statusIds = Arrays.asList(1, 2);
            List<BookingSeat> bookedSeatsList = bookingSeatRepositories.
                    getByMovieSessionMovieSessionIdAndDictBookingStatusIdIn(
                            bookingMovieSessionDto.getMovieSessionId(), statusIds);

            for (BookingSeatDto bookingSeatDto : bookingMovieSessionDto.getBookingSeatDtoList()) {
                if (bookedSeatsList.stream().anyMatch(seat -> seat.getSeat().getSeatId() == bookingSeatDto.getSeatId())) {
                    throw new MyException("Seat " + bookingSeatDto.getSeatId() + " is already booked.");
                }
                DictBookingStatus dictBookingStatusForBookingSeat = dictBookingStatusRepositories.
                        findByDictBookingStatusId(bookingSeatDto.getBookingStatus());
                if (dictBookingStatusForBookingSeat == null) {
                    throw new MyException("Booking status for seat not found!");
                }

                Seat seat = seatRepositories.findBySeatId(bookingSeatDto.getSeatId());
                if (seat == null){
                    throw new MyException("Seat not found.");
                }
                Price price = priceRepositories.
                        findByMovieSessionAndDictSeatClass(movieSession, seat.getDictSeatClass());

                BookingSeat bookingSeat = new BookingSeat(
                        booking,
                        movieSession,
                        price,
                        seat,
                        dictBookingStatus
                );
                bookingSeatList.add(bookingSeat);
            }
        }
        try {
            saveBookingWithSeats(booking, bookingSeatList);
        }
        catch (Exception e){
        }

        BookingResponseDto bookingResponseDto = new BookingResponseDto(
                bookingNumber
        );
        return bookingResponseDto;
    }

    @Override
    public BookingResponseDto updateBooking(BookingAddBookingRequestDto bookingAddBookingRequestDto) {
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(bookingAddBookingRequestDto.getToken());
        User user = userRepositories.findUserByEmail(jwtClaims.getEmail());
        if (user == null) {
            throw new MyException("User not found!");
        }
        DictBookingStatus dictBookingStatus = dictBookingStatusRepositories.
                findByDictBookingStatusId(bookingAddBookingRequestDto.getBookingStatus());
        if (dictBookingStatus == null) {
            throw new MyException("Booking status not found!");
        }
        Booking booking = bookingRepositories.findByBookingNumber(bookingAddBookingRequestDto.getBookingNumber());

        for (BookingMovieSessionDto bookingMovieSessionDto : bookingAddBookingRequestDto.getBookingMovieSessionDtoList()) {
            MovieSession movieSession = movieSessionRepositories.
                    findByMovieSessionId(bookingMovieSessionDto.getMovieSessionId());
            List<BookingSeat> bookingSeatList = bookingSeatRepositories.
                    findByBookingBookingIdAndMovieSessionMovieSessionId(
                            booking.getBookingId(), bookingMovieSessionDto.getMovieSessionId());

            DictBookingStatus dictBookingStatusCancelled = dictBookingStatusRepositories.findByDictBookingStatusId(3);
            for (BookingSeat bookingSeat : bookingSeatList) {
                boolean isSeatPresent = false;
                for (BookingSeatDto bookingSeatDto : bookingMovieSessionDto.getBookingSeatDtoList()) {
                    if (bookingSeatDto.getSeatId() == bookingSeat.getSeat().getSeatId()) {
                        isSeatPresent = true;
                        break;
                    }
                }
                if (!isSeatPresent) {
                    bookingSeat.setDictBookingStatus(dictBookingStatusCancelled);
                    bookingSeatRepositories.save(bookingSeat);
                }
                else {
                    bookingSeat.setDictBookingStatus(dictBookingStatus);
                    bookingSeatRepositories.save(bookingSeat);
                }
            }

            for (BookingSeatDto bookingSeatDto : bookingMovieSessionDto.getBookingSeatDtoList()){
                boolean seatExists = false;
                for (BookingSeat bookingSeat : bookingSeatList) {
                    if (bookingSeat.getSeat().getSeatId() == bookingSeatDto.getSeatId()) {
                        seatExists = true;
                        bookingSeat.setDictBookingStatus(dictBookingStatus);
                        bookingSeatRepositories.save(bookingSeat);
                        break;
                    }
                }
                if (!seatExists) {
                    Seat seat = seatRepositories.findBySeatId(bookingSeatDto.getSeatId());
                    if (seat == null) {
                        throw new MyException("Seat not found.");
                    }
                    Price price = priceRepositories.findByMovieSessionAndDictSeatClass(movieSession, seat.getDictSeatClass());
                    BookingSeat newBookingSeat = new BookingSeat(
                            booking,
                            movieSession,
                            price,
                            seat,
                            dictBookingStatus
                    );
                    bookingSeatRepositories.save(newBookingSeat);
                }

            }
        }
        booking.setDictBookingStatus(dictBookingStatus);
        bookingRepositories.save(booking);
        BookingResponseDto bookingResponseDto = new BookingResponseDto(bookingAddBookingRequestDto.getBookingNumber());
        return bookingResponseDto;
    }

    @Override
    public List<BookingUserResponseDto> getBookingsByUserId(String token) {
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(token);
        User user = userRepositories.findUserByEmail(jwtClaims.getEmail());
        return bookingRepositories.getBookingsByUserId(user.getUserId()).stream().map(
                booking -> new BookingUserResponseDto(
                        booking.getBookingId(),
                        booking.getBookingNumber(),
                        booking.getTotalPrice(),
                        bookingRepositories.getMovieSessionByBookingId(booking.getBookingId()).getCinemaHall().getCinema().getName(),
                        bookingRepositories.getMovieSessionByBookingId(booking.getBookingId()).getMovieId().getName(),
                        bookingRepositories.getMovieSessionByBookingId(booking.getBookingId()).getDateOfSession(),
                        booking.getDictBookingStatus()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<BookingSeatResponseDto> getBookingDetailsByBookingId(int bookingId) {
        return bookingRepositories.getBookingDetailsByBookingId(bookingId).stream().map(
                bookingDetails -> new BookingSeatResponseDto(
                        bookingDetails.getSeat().toString(),
                        bookingDetails.getPrice().getPrice(),
                        bookingDetails.getMovieSession().getMovieId().getName(),
                        bookingDetails.getMovieSession().getCinemaHall().getCinema().getName(),
                        bookingDetails.getMovieSession().getCinemaHall().getName(),
                        bookingDetails.getMovieSession().getMovieSessionId(),
                        bookingDetails.getBooking().getBookingNumber()
                )
        ).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveBookingWithSeats(Booking booking, List<BookingSeat> bookingSeatList) throws Exception {
        try {
            bookingRepositories.save(booking);
            for (BookingSeat bookingSeat : bookingSeatList) {
                bookingSeatRepositories.save(bookingSeat);
            }
        } catch (Exception e) {
            throw new Exception("Failed to save booking and booking seats", e);
        }
    }
}
