package com.example.cinemaressys.services.datagenerator;

import com.example.cinemaressys.entities.*;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {
    private CinemaRepositories cinemaRepositories;
    private CinemaHallRepositories cinemaHallRepositories;
    private DictBookingStatusRepositories dictBookingStatusRepositories;
    private DictSeatClassRepositories dictSeatClassRepositories;
    private DictSessionTypeRepositories dictSessionTypeRepositories;
    private GenreRepositories genreRepositories;
    private MovieRepositories movieRepositories;
    private RoleRepositories roleRepositories;
    private SeatRepositories seatRepositories;
    private MovieSessionRepositories movieSessionRepositories;
    private PriceRepositories priceRepositories;
    private UserRepositories userRepositories;
    private BookingRepositories bookingRepositories;
    private BookingSeatRepositories bookingSeatRepositories;

    public DataGeneratorServiceImpl(
            CinemaRepositories cinemaRepositories,
            CinemaHallRepositories cinemaHallRepositories,
            DictBookingStatusRepositories dictBookingStatusRepositories,
            DictSeatClassRepositories dictSeatClassRepositories,
            DictSessionTypeRepositories dictSessionTypeRepositories,
            GenreRepositories genreRepositories,
            MovieRepositories movieRepositories,
            RoleRepositories roleRepositories,
            SeatRepositories seatRepositories,
            MovieSessionRepositories movieSessionRepositories,
            PriceRepositories priceRepositories,
            UserRepositories userRepositories,
            BookingRepositories bookingRepositories,
            BookingSeatRepositories bookingSeatRepositories) {
        this.cinemaRepositories = cinemaRepositories;
        this.cinemaHallRepositories = cinemaHallRepositories;
        this.dictBookingStatusRepositories = dictBookingStatusRepositories;
        this.dictSeatClassRepositories = dictSeatClassRepositories;
        this.dictSessionTypeRepositories = dictSessionTypeRepositories;
        this.genreRepositories = genreRepositories;
        this.movieRepositories = movieRepositories;
        this.roleRepositories = roleRepositories;
        this.seatRepositories = seatRepositories;
        this.movieSessionRepositories = movieSessionRepositories;
        this.priceRepositories = priceRepositories;
        this.userRepositories = userRepositories;
        this.bookingRepositories = bookingRepositories;
        this.bookingSeatRepositories = bookingSeatRepositories;
    }

    List<String> dictBookingStatuses = new ArrayList<>(Arrays.asList("Pending", "Confirmed", "Cancelled", "Completed"));
    List<String> dictSeatClasses = Arrays.asList("VIP", "Premium", "Standard");
    List<String> sessionTypes = Arrays.asList("2D", "3D", "IMAX 2D", "IMAX 3D", "RealD 3D");
    List<String> roles = Arrays.asList("Administrator", "User", "Cinema manager");
    List<String> genres = Arrays.asList(
            "Action", "Adventure", "Animation", "Biography", "Comedy", "Crime",
            "Documentary", "Drama", "Family", "Fantasy", "Film Noir", "History",
            "Horror", "Music", "Musical", "Mystery", "Romance", "Sci-Fi",
            "Sport", "Thriller", "War", "Western"
    );
    List<String[]> cinemasData = Arrays.asList(
            new String[]{"Warsaw, Poland", "Cinema City Arkadia", "Warsaw", "22-123-4567", "cinema1@example.com"},
            new String[]{"Krakow, Poland", "Kino Kijów", "Krakow", "12-345-6789", "cinema2@example.com"},
            new String[]{"Wroclaw, Poland", "Multikino Wroclavia", "Wroclaw", "71-234-5678", "cinema3@example.com"},
            new String[]{"Gdansk, Poland", "Helios Forum Gdansk", "Gdansk", "58-123-4567", "cinema4@example.com"},
            new String[]{"Poznan, Poland", "Cinema City Poznan", "Poznan", "61-234-5678", "cinema5@example.com"}
    );
    List<String[]> moviesData = Arrays.asList(
            new String[]{
                    "Forrest Gump is a 1994 American comedy-drama film directed by Robert Zemeckis and written by Eric Roth. It is based on the 1986 novel of the same name by Winston Groom and stars Tom Hanks, Robin Wright, Gary Sinise, and Sally Field.",
                    "Robert Zemeckis", "142", "Forrest Gump", "USA", "1994-07-06", "12"
            },
            new String[]{
                    "Inception is a 2010 science fiction action film directed by Christopher Nolan. It stars Leonardo DiCaprio as a professional thief who steals information by infiltrating the subconscious of his targets.",
                    "Christopher Nolan", "148", "Inception", "USA", "2010-07-16", "14"
            },
            new String[]{
                    "The Matrix is a 1999 science fiction action film written and directed by the Wachowskis. It is the first installment in The Matrix film series, starring Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving, and Joe Pantoliano.",
                    "The Wachowskis", "136", "The Matrix", "USA", "1999-03-31", "14"
            },
            new String[]{
                    "The Silence of the Lambs is a 1991 American psychological horror film directed by Jonathan Demme and written by Ted Tally, adapted from Thomas Harris's 1988 novel of the same name.",
                    "Jonathan Demme", "118", "The Silence of the Lambs", "USA", "1991-02-14", "18"
            },
            new String[]{
                    "The Green Mile is a 1999 American fantasy crime drama film directed by Frank Darabont, based on the Stephen King novel.",
                    "Frank Darabont", "189", "The Green Mile", "USA", "1999-12-10", "16"
            },
            new String[]{
                    "The Shawshank Redemption is a 1994 American drama film written and directed by Frank Darabont, based on the 1982 Stephen King novella Rita Hayworth and Shawshank Redemption. It stars Tim Robbins and Morgan Freeman.",
                    "Frank Darabont", "142", "The Shawshank Redemption", "USA", "1994-09-23", "16"
            },
            new String[]{
                    "Pulp Fiction is a 1994 American neo-noir black comedy crime film written and directed by Quentin Tarantino.",
                    "Quentin Tarantino", "154", "Pulp Fiction", "USA", "1994-10-14", "18"
            },
            new String[]{
                    "The Godfather is a 1972 American crime film directed by Francis Ford Coppola, who co-wrote the screenplay with Mario Puzo, based on Puzo's best-selling 1969 novel of the same name.",
                    "Francis Ford Coppola", "175", "The Godfather", "USA", "1972-03-24", "18"
            },
            new String[]{
                    "The Dark Knight is a 2008 superhero film directed, produced, and co-written by Christopher Nolan. Based on the DC Comics character Batman, the film is the second installment of Nolan's The Dark Knight Trilogy and a sequel to 2005's Batman Begins.",
                    "Christopher Nolan", "152", "The Dark Knight", "USA", "2008-07-18", "14"
            },
            new String[]{
                    "Schindler's List is a 1993 American epic historical drama film directed and produced by Steven Spielberg and written by Steven Zaillian. It is based on the 1982 novel Schindler's Ark by Australian novelist Thomas Keneally.",
                    "Steven Spielberg", "195", "Schindler's List", "USA", "1993-11-30", "18"
            },
            new String[]{
                    "Fight Club is a 1999 American film directed by David Fincher and starring Brad Pitt, Edward Norton, and Helena Bonham Carter. It is based on the 1996 novel of the same name by Chuck Palahniuk.",
                    "David Fincher", "139", "Fight Club", "USA", "1999-10-15", "18"
            },
            new String[]{
                    "Goodfellas is a 1990 American crime film directed by Martin Scorsese, produced by Irwin Winkler and distributed by Warner Bros. It is an adaptation of the 1985 non-fiction book Wiseguy by Nicholas Pileggi, who co-wrote the screenplay with Scorsese.",
                    "Martin Scorsese", "146", "Goodfellas", "USA", "1990-09-19", "18"
            },
            new String[]{
                    "The Lord of the Rings: The Return of the King is a 2003 epic fantasy adventure film directed by Peter Jackson, based on the third volume of J. R. R. Tolkien's The Lord of the Rings.",
                    "Peter Jackson", "201", "The Lord of the Rings: The Return of the King", "USA", "2003-12-17", "14"
            },
            new String[]{
                    "The Departed is a 2006 American crime thriller film directed by Martin Scorsese and written by William Monahan. It is a remake of the 2002 Hong Kong film Infernal Affairs.",
                    "Martin Scorsese", "151", "The Departed", "USA", "2006-10-06", "18"
            }
    );

    @Override
    public void generateDataAll() {
        try {
            Random random = new Random();
            for (String status : this.dictBookingStatuses) {
                dictBookingStatusRepositories.save(new DictBookingStatus(status));
            }
            for (String seatClass : this.dictSeatClasses) {
                dictSeatClassRepositories.save(new DictSeatClass(seatClass));
            }
            for (String sessionType : this.sessionTypes) {
                dictSessionTypeRepositories.save(new DictSessionType(sessionType));
            }
            for (String genre : this.genres) {
                genreRepositories.save(new Genre(genre));
            }
            for (String role : this.roles) {
                roleRepositories.save(new Role(role));
            }
            User user = new User("test", "test", "test@test.pl", "test",
                    LocalDate.of(2002, 7, 2), roleRepositories.findByName("User"));
            userRepositories.save(user);

            for (String[] movieData : this.moviesData) {
                try {
                    Movie movie = new Movie();
                    movie.setDescription(movieData[0]);
                    movie.setDirector(movieData[1]);
                    movie.setDuration(Integer.parseInt(movieData[2]));
                    movie.setName(movieData[3]);
                    movie.setProductionCountry(movieData[4]);
                    movie.setReleaseDate(LocalDate.parse(movieData[5]));
                    movie.setMinimumAge(Integer.parseInt(movieData[6]));

                    Set<Genre> randomGenres = new HashSet<>();
                    int numGenres = random.nextInt(3) + 1;
                    for (int i = 0; i < numGenres; i++) {
                        Genre randomGenre = getRandomGenre();
                        randomGenres.add(randomGenre);
                    }
                    movie.setMovieGenres(randomGenres);

                    movieRepositories.save(movie);
                } catch (Exception e) {
                    // Obsługa błędu dla pojedynczego filmu
                    System.err.println("Błąd podczas zapisywania filmu: " + Arrays.toString(movieData));
                    e.printStackTrace();
                }
            }

            for (String[] cinemaData : this.cinemasData) {
                try {
                    Cinema cinema = new Cinema(cinemaData[0], cinemaData[1], cinemaData[2], cinemaData[3], cinemaData[4]);
                    cinemaRepositories.save(cinema);
                    this.generateCinemaHalls(cinema, user);
                } catch (Exception e) {
                    // Obsługa błędu dla pojedynczego kina
                    System.err.println("Błąd podczas zapisywania kina: " + Arrays.toString(cinemaData));
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new MyException("Wystąpił nieoczekiwany błąd podczas generowania danych.", e);
        }
    }


    private Genre getRandomGenre() {
        Random random = new Random();
        int randomIndex = random.nextInt(genres.size());
        String randomGenreName = genres.get(randomIndex);
        return genreRepositories.findByName(randomGenreName);
    }

    public void generateCinemaHalls(Cinema cinema, User user) {
        Random random = new Random();
        int randomCountHalls = random.nextInt(5, 10);
        for (int i = 1; i <= randomCountHalls; i++) {
            CinemaHall cinemaHall = new CinemaHall();
            cinemaHall.setName("Hall " + i);
            cinemaHall.setCinema(cinema);
            cinemaHallRepositories.save(cinemaHall);
            this.generateSeats(cinemaHall);
            this.generateMovieSession(cinemaHall, user);
        }
    }

    public void generateSeats(CinemaHall cinemaHall) {
        Random random = new Random();
        int howManyRows = random.nextInt(5, 15);
        for (int i = 0; i < howManyRows; i++) {
            char rowLetter = (char) ('a' + i);
            int howManyColumn = random.nextInt(5, 20);
            int randomIndex = random.nextInt(dictSeatClasses.size());
            String randomSeatClass = dictSeatClasses.get(randomIndex);
            DictSeatClass dictSeatClass = dictSeatClassRepositories.findByName(randomSeatClass);

            for (int j = 1; j <= howManyColumn; j++) {
                Seat seat = new Seat(cinemaHall, dictSeatClass, rowLetter, j);
                seatRepositories.save(seat);
            }
        }
    }

    @Override
    public void generateMovieSession(CinemaHall cinemaHall, User user) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMonths(2);

        Random random = new Random();
        LocalDateTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            int randomMovie = random.nextInt(this.moviesData.size());
            String[] selectedMovie = moviesData.get(randomMovie);
            Movie movie = movieRepositories.findByName(selectedMovie[3]);

            boolean isSubtitles = random.nextBoolean();
            int randomSessionIntex = random.nextInt(sessionTypes.size());
            String randomSessionType = sessionTypes.get(randomSessionIntex);
            DictSessionType dictSessionType = dictSessionTypeRepositories.findByName(randomSessionType);

            currentTime = currentTime.plusMinutes(movie.getDuration());
            int randomMinutes = random.nextInt(31) + 30;
            currentTime = currentTime.plusMinutes(randomMinutes);

            if (currentTime.getHour() >= 22 || currentTime.getHour() < 10) {
                currentTime = currentTime.plusDays(1);
                currentTime = currentTime.withHour(10).withMinute(0);
            }

            MovieSession movieSession = new MovieSession(movie, cinemaHall, dictSessionType, currentTime.toLocalDate(),
                    currentTime.toLocalTime(), isSubtitles);

            movieSessionRepositories.save(movieSession);
            this.generatePrice(movieSession);
            this.generateBooking(movieSession, user);
        }

    }

    public void generatePrice(MovieSession movieSession) {
        Random random = new Random();

        for (int j = 0 ; j < 3; j++) {
            String selectedClasses = this.dictSeatClasses.get(j);
            DictSeatClass dictSeatClass = dictSeatClassRepositories.findByName(selectedClasses);

            float price = random.nextInt(10, 50);
            Price newPrice = new Price(
                    movieSession,
                    dictSeatClass,
                    price
            );
            priceRepositories.save(newPrice);
        }
    }

    public void generateBooking(MovieSession movieSession, User user) {
        Random random = new Random();
        Integer bookingNumber = bookingRepositories.findMaxBookingNumber();
        if (bookingNumber == null){
            bookingNumber = 0;
        }
        bookingNumber += 1;
        String dictBookingStatusName = "Confirmed";
        Booking booking = new Booking();
        booking.setBookingNumber(bookingNumber);
        booking.setUser(user);
        booking.setDictBookingStatus(dictBookingStatusRepositories.findByName(dictBookingStatusName));

        int howManySeats = random.nextInt(1, 15);
        List<Seat> seatList = seatRepositories.findByCinemaHallCinemaHallId(movieSession.getCinemaHall().getCinemaHallId());

        Collections.shuffle(seatList);
        List<Seat> randomlySelectedSeats = seatList.subList(0, Math.min(howManySeats, seatList.size()));
        float sumPrice = 0;
        List<BookingSeat> bookingSeatList = new ArrayList<>();
        for (Seat seat : randomlySelectedSeats){
            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBooking(booking);
            bookingSeat.setDictBookingStatus(dictBookingStatusRepositories.findByName(dictBookingStatusName));
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
