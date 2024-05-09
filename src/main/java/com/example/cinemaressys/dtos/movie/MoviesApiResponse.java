package com.example.cinemaressys.dtos.movie;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviesApiResponse {
    private List<MovieApiResponse> results;

}
