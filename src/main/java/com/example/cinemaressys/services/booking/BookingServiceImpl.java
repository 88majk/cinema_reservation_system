package com.example.cinemaressys.services.booking;

import com.example.cinemaressys.dtos.booking.BookingAddBookingRequestDto;
import com.example.cinemaressys.dtos.booking.BookingMovieSessionDto;
import com.example.cinemaressys.dtos.booking.BookingResponseDto;
import com.example.cinemaressys.dtos.booking.BookingSeatDto;
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
import java.util.List;

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

            for (BookingSeatDto bookingSeatDto : bookingMovieSessionDto.getBookingSeatDtoList()) {
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
