package com.example.cinemaressys.dtos.moviesession;

import com.example.cinemaressys.entities.Movie;
import com.example.cinemaressys.entities.MovieSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieSessionResponse {
    List<MovieDto> movieAndSessions;
}
