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
    CommandLineRunner commandLineRunner(MovieRepository movieRepo, RoomRepository roomRepo, SeatRepository seatRepo) {
        return args -> {
            Room theater1 = new Room("Theater 1", 20);
            roomRepo.save(theater1);

            // replace with call to roomService
            for (int i = 0; i < 4; i++) {
                char row = (char) ('A' + i);
                for (int j = 1; j <= 5; j++) {
                    seatRepo.save(new Seat(row + String.valueOf(j), theater1));
                }
            }

            Movie iceAge = new Movie(
                    "Ice Age",
                    LocalDate.of(2002, 3, 15),
                    "Animation",
                    "A sloth, a mammoth, and a saber-toothed tiger team up.",
                    12.50,
                    12
                    ,30
            )
                    ;
            iceAge.getRooms().add(theater1);
            movieRepo.save(iceAge);
        };
    }
}
