package org.example.movie_theater;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Repos.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class TheaterConfig {

    @Bean
    CommandLineRunner theatercommandLineRunner(MovieRepository movieRepository) {
        return args -> {
            Movie iceAge = new Movie(
                    "Ice Age",
                    LocalDate.of(2002, 3, 15),
                    "Animation",
                    "A sloth, a mammoth, and a saber-toothed tiger team up.",
                    12.50f
            );

            movieRepository.saveAll(List.of(iceAge));
        };
    }
}

