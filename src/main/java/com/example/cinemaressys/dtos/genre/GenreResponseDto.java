package com.example.cinemaressys.dtos.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GenreResponseDto {
    private int genreId;
    private String name;
}
