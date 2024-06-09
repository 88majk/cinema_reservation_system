package com.example.cinemaressys.services.movie;

import com.example.cinemaressys.dtos.movie.MovieApiResponse;
import com.example.cinemaressys.dtos.movie.MovieRequestDto;
import com.example.cinemaressys.dtos.movie.MovieResponseDto;
import com.example.cinemaressys.dtos.movie.MoviesApiResponse;
import com.example.cinemaressys.entities.Genre;
import com.example.cinemaressys.entities.Movie;
import com.example.cinemaressys.repositories.GenreRepositories;
import com.example.cinemaressys.repositories.MovieRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepositories movieRepository;
    private final GenreRepositories genreRepository;
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void addMovie(MovieRequestDto requestDto) {
        movieRepository.save(new Movie(
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getReleaseDate(),
                requestDto.getMinimumAge(),
                requestDto.getDuration(),
                requestDto.getProductionCountry(),
                requestDto.getDirector(),
                requestDto.getMovieGenres()
        ));
    }

    @Override
    public List<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> {
                    Set<Genre> movieGenres = movieRepository.findGenresByMovieId(movie.getMovieId());
                    return new MovieResponseDto(
                            movie.getMovieId(),
                            movie.getName(),
                            movie.getDescription(),
                            movie.getReleaseDate(),
                            movie.getMinimumAge(),
                            movie.getDuration(),
                            movie.getProductionCountry(),
                            movie.getDirector(),
                            movieGenres
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDto getMovie(int movieId) {
        Optional<Movie> movieById = movieRepository.findById(movieId);
        return movieById.map(
                movie -> {
                    Set<Genre> movieGenres = movieRepository.findGenresByMovieId(movieId);
                    return new MovieResponseDto(
                            movie.getMovieId(),
                            movie.getName(),
                            movie.getDescription(),
                            movie.getReleaseDate(),
                            movie.getMinimumAge(),
                            movie.getDuration(),
                            movie.getProductionCountry(),
                            movie.getDirector(),
                            movieGenres
                    );
                }
        ).orElse(null);
    }

    @Override //PROBLEM Z UPDATEM (PRZEZ GENRE)
    public void updateMovie(int movieId, MovieRequestDto requestDto) {
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + movieId + " not found!"));

        Set<Genre> movieGenres = movieRepository.findGenresByMovieId(movieId);
        Set<Genre> newGenres = new HashSet<>();

        existingMovie.setName(requestDto.getName());
        existingMovie.setDescription(requestDto.getDescription());
        existingMovie.setReleaseDate(requestDto.getReleaseDate());
        existingMovie.setMinimumAge(requestDto.getMinimumAge());
        existingMovie.setDuration(requestDto.getDuration());
        existingMovie.setProductionCountry(requestDto.getProductionCountry());
        existingMovie.setDirector(requestDto.getDirector());

        for (Genre newGenre: requestDto.getMovieGenres()) {
            boolean exists = false;
            for (Genre genres: movieGenres) {
                if(newGenre.getGenreId() == genres.getGenreId()){
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                newGenres.add(newGenre);
            }
        }

        existingMovie.setMovieGenres(newGenres);

        movieRepository.save(existingMovie);
    }

    @Override
    public Boolean deleteMovie(int movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
            return true;
        } else
            return false;
    }

    public List<Movie> getMoviesFromApi() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            MoviesApiResponse apiResponse = mapper.readValue(response.body().string(), MoviesApiResponse.class);
            return mapApiResponseToMovies(apiResponse);
        }
    }

    private List<Movie> mapApiResponseToMovies(MoviesApiResponse apiResponse) {
        List<Movie> movies = new ArrayList<>();
        for (MovieApiResponse movieApiResponse : apiResponse.getResults()) {
            Movie movie = new Movie();
            movie.setName(movieApiResponse.getTitle());
            movie.setDescription(movieApiResponse.getOverview());
            movie.setReleaseDate(LocalDate.parse(movieApiResponse.getRelease_date()));
            // Ustaw pozostałe pola zgodnie z danymi z API
            movie.setMinimumAge(0); // W tym miejscu możesz ustawić minimalny wiek na podstawie danych z API
            movie.setDuration(0); // W tym miejscu możesz ustawić długość filmu na podstawie danych z API
            movie.setProductionCountry(""); // W tym miejscu możesz ustawić kraj produkcji na podstawie danych z API
            movie.setDirector(""); // W tym miejscu możesz ustawić reżysera na podstawie danych z API
            // Jeśli masz informacje o gatunkach filmu, możesz je dodać
            Set<Genre> genres = new HashSet<>();
            for (Integer genreId : movieApiResponse.getGenre_ids()) {
                Genre genre = new Genre();
                genre.setGenreId(genreId); // Jeśli masz id gatunku, ustaw je
                // Pobierz i ustaw pozostałe pola gatunku
                genres.add(genre);
            }
            movie.setMovieGenres(genres);
            movies.add(movie);
        }
        return movies;
    }
}
