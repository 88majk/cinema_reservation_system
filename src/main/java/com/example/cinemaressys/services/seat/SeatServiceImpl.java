package com.example.cinemaressys.services.seat;

import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;
import com.example.cinemaressys.dtos.seat.RowDto;
import com.example.cinemaressys.dtos.seat.SeatDto;
import com.example.cinemaressys.dtos.seat.SeatsResponseDto;
import com.example.cinemaressys.entities.*;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService{
    final private CinemaHallRepositories cinemaHallRepositories;
    final private SeatRepositories seatRepositories;
    final private DictSeatClassRepositories dictSeatClassRepositories;
    final private MovieSessionRepositories movieSessionRepositories;
    final private BookingSeatRepositories bookingSeatRepositories;
    final private PriceRepositories priceRepositories;
    final private UserRepositories userRepositories;
    final private DictBookingStatusRepositories dictBookingStatusRepositories;
    final private BookingRepositories bookingRepositories;

    @Override
    public void createSeatsInNewHall(ListSeatRequestDto listSeatRequestDto) {
        if (cinemaHallRepositories.getCinemaHallByCinemaHallId(listSeatRequestDto.getCinemaHallId()) == null){
            throw new MyException("The cinema room with the given ID does not exist.");
        }
        CinemaHall cinemaHall = cinemaHallRepositories.getCinemaHallByCinemaHallId(listSeatRequestDto.getCinemaHallId());
        List<Seat> seatCinemaHall = seatRepositories.findByCinemaHallCinemaHallId(listSeatRequestDto.getCinemaHallId());
        if (!seatCinemaHall.isEmpty()){
            throw new MyException("This cinema room already has assigned seats. " + seatCinemaHall.get(0).getSeatId());
        }
        else{
            for (SeatDto seatDto : listSeatRequestDto.getSeats()){
                DictSeatClass dictSeatClass = dictSeatClassRepositories.findByName(seatDto.getSeatClass());
                if (dictSeatClass == null){
                    throw new MyException("This class does not exist in cinema.");
                }
//                for (int i=1;i<=seatDto.getColumns();i++){
//                    Seat seat = new Seat(
//                            cinemaHall,
//                            dictSeatClass,
//                            seatDto.get(),
//                            i
//                    );
//                    seatRepositories.save(seat);
//                }

            }
        }

    }

    @Override
    public SeatsResponseDto getSeatsByMovieSessionId(int movieSessionId) {
//        insertPrice();
//        insert100Booking();

        MovieSession movieSession = movieSessionRepositories.findByMovieSessionId(movieSessionId);
        if(movieSession == null){
            throw new MyException("movieSession with Id: " + movieSessionId + " not found");
        }
        List<Seat> seatList = seatRepositories.findByCinemaHallCinemaHallId(
                movieSession.getCinemaHall().getCinemaHallId());
        if (seatList.isEmpty()){
            throw new MyException("Seats in cinema hall are not found.");
        }
        List<Price> priceList = priceRepositories.findByMovieSessionMovieSessionId(movieSessionId);
        if (priceList.isEmpty()){
            throw new MyException("Price for seats are not found.");
        }
        Map<Integer, Float> priceMap = new HashMap<>();
        for (Price price : priceList) {
            priceMap.put(price.getDictSeatClass().getDictSeatClassId(), price.getPrice());
        }
        List<BookingSeat> bookingSeatList = bookingSeatRepositories.getByMovieSessionMovieSessionId(movieSessionId);
        Map<Character, List<SeatDto>> rowMap = new HashMap<>();
        List<SeatDto> seatDtoList = new ArrayList<>();

        for (Seat seat : seatList){
            Character rowName = seat.getRowNumber();

            if (!rowMap.containsKey(rowName)) {
                // Jeśli nie istnieje, utwórz nowy obiekt RowDto i dodaj go do mapy
                rowMap.put(rowName, new ArrayList<>());
            }
            List<SeatDto> seatDtosForRow = rowMap.get(rowName);

            SeatDto seatDto = new SeatDto();
            seatDto.setSeatId(seat.getSeatId());
            seatDto.setSeatClass(seat.getDictSeatClass().getName());
            seatDto.setColumn(seat.getColumnNumber());

            if (priceMap.containsKey(seat.getDictSeatClass().getDictSeatClassId())) {
                seatDto.setPrice(priceMap.get(seat.getDictSeatClass().getDictSeatClassId()));
            } else {
                seatDto.setPrice(0);
            }
            BookingSeat foundBookingSeat = null;
            for (BookingSeat bookingSeat : bookingSeatList) {
                if (bookingSeat.getSeat().getSeatId() == seatDto.getSeatId()) {
                    foundBookingSeat = bookingSeat;
                    break;
                }
            }
            if (foundBookingSeat != null){
                seatDto.setAvailable(false);
                seatDto.setBookingStatus(foundBookingSeat.getDictBookingStatus().getName());
            }
            else{
                seatDto.setAvailable(true);
                seatDto.setBookingStatus(null);
            }
            seatDto.setRow(seat.getRowNumber());
            seatDtosForRow.add(seatDto);
        }

        // Iteruj po mapie i utwórz obiekty RowDto
        List<RowDto> rowDtoList = new ArrayList<>();
        for (Map.Entry<Character, List<SeatDto>> entry : rowMap.entrySet()) {
            RowDto rowDto = new RowDto();

            List<SeatDto> sortedSeatDtoList = entry.getValue();
            sortedSeatDtoList.sort(Comparator.comparingInt(SeatDto::getSeatId));

            rowDto.setRowName(entry.getKey().toString()); // Konwersja Character na String
            rowDto.setSeats(sortedSeatDtoList);
            rowDtoList.add(rowDto);
        }

        SeatsResponseDto seatsResponseDto = new SeatsResponseDto(
                movieSession.getCinemaHall().getCinemaHallId(),
                movieSession.getCinemaHall().getName(),
                rowDtoList
        );

        return seatsResponseDto;
    }

    void insert100Booking(){
        Random random = new Random();
        for (int i = 76; i < 104; i++) {
            int bookingNumber = 20000000 + i;
            int dictBookingStatusId = 2;
            int userId;
            do {
                userId = random.nextInt(1, 11);
            } while (userId == 3 || userId == 4);
            Booking booking = new Booking();
            booking.setBookingNumber(bookingNumber);
            booking.setUser(userRepositories.findByUserId(userId));
            booking.setDictBookingStatus(dictBookingStatusRepositories.findByDictBookingStatusId(dictBookingStatusId));

            int howManySeats = random.nextInt(1, 15);
            MovieSession movieSession = movieSessionRepositories.findByMovieSessionId(i);
            List<Seat> seatList = seatRepositories.findByCinemaHallCinemaHallId(movieSession.getCinemaHall().getCinemaHallId());

            Collections.shuffle(seatList);
            List<Seat> randomlySelectedSeats = seatList.subList(0, Math.min(howManySeats, seatList.size()));
            float sumPrice = 0;
            List<BookingSeat> bookingSeatList = new ArrayList<>();
            for (Seat seat : randomlySelectedSeats){
                BookingSeat bookingSeat = new BookingSeat();
                bookingSeat.setBooking(booking);
                bookingSeat.setDictBookingStatus(dictBookingStatusRepositories.findByDictBookingStatusId(random.nextInt(1,3)));
                bookingSeat.setMovieSession(movieSession);
                bookingSeat.setSeat(seat);
                Price price = priceRepositories.findByMovieSessionAndDictSeatClass(movieSession, seat.getDictSeatClass());
                bookingSeat.setPrice(price);
                sumPrice += price.getPrice();

                bookingSeatList.add(bookingSeat);
            }
            booking.setTotalPrice(sumPrice);
            bookingRepositories.save(booking);
            for(BookingSeat bookingSeat : bookingSeatList){
                bookingSeatRepositories.save(bookingSeat);

            }

        }
    }

    void insertPrice() {
        Random random = new Random();

        for (int i = 76; i < 104; i++){
            MovieSession movieSession = movieSessionRepositories.findByMovieSessionId(i);
            for (int j = 1 ; j < 4; j++) {
                DictSeatClass dictSeatClass = dictSeatClassRepositories.findByDictSeatClassId(j);
                float price = random.nextInt(10, 50);
                Price newPrice = new Price(
                        movieSession,
                        dictSeatClass,
                        price
                );
                priceRepositories.save(newPrice);
            }
        }
    }


}
