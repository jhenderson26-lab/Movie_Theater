package org.example.movie_theater;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Repos.MovieRepository;
import org.example.movie_theater.Repos.RoomRepository;
import org.example.movie_theater.Repos.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Configuration
public class TheaterConfig {

    @Bean
    CommandLineRunner theatercommandLineRunner(MovieRepository movieRepository, RoomRepository roomRepository, SeatRepository seatRepository) {
        return args -> {
            Movie iceAge = new Movie(
                    "Ice Age",
                    LocalDate.of(2002, 3, 15),
                    "Animation",
                    "A sloth, a mammoth, and a saber-toothed tiger team up.",
                    12.50,
                    30
                    ,12
            );
            Room one = new Room("1", 50, Collections.singletonList(iceAge));

            Seat seat4one = new Seat();

            movieRepository.saveAll(List.of(iceAge));
            roomRepository.saveAll(List.of(one));
        };
    }
}

