package com.example.cinemaressys.services.seat;

import com.example.cinemaressys.dtos.seat.ListSeatRequestDto;
import com.example.cinemaressys.dtos.seat.SeatDto;
import com.example.cinemaressys.entities.CinemaHall;
import com.example.cinemaressys.entities.DictSeatClass;
import com.example.cinemaressys.entities.Seat;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.CinemaHallRepositories;
import com.example.cinemaressys.repositories.DictSeatClassRepositories;
import com.example.cinemaressys.repositories.SeatRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService{
    final private CinemaHallRepositories cinemaHallRepositories;
    final private SeatRepositories seatRepositories;
    final private DictSeatClassRepositories dictSeatClassRepositories;

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
                for (int i=1;i<=seatDto.getColumns();i++){
                    Seat seat = new Seat(
                            cinemaHall,
                            dictSeatClass,
                            seatDto.getRow(),
                            i
                    );
                    seatRepositories.save(seat);
                }

            }
        }

    }
}
