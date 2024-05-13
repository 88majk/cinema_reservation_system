package com.example.cinemaressys.services.moviesession;

import com.example.cinemaressys.dtos.moviesession.MovieSessionResponse;

public interface MovieSessionService {
    MovieSessionResponse getMoviesSessions(int cinemaId, String movieSessionDate);
}
