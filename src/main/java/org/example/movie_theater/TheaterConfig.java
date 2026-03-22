package org.example.movie_theater;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Repos.MovieRepository;
import org.example.movie_theater.Repos.RoomRepository;
import org.example.movie_theater.Repos.SeatRepository;
import org.example.movie_theater.User.User;
import org.example.movie_theater.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TheaterConfig {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }
        };
    }

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepo, RoomRepository roomRepo, SeatRepository seatRepo) {
        return args -> {
            Room t1 = new Room("Theater 1", 40);
            Room t2 = new Room("Theater 2", 40);
            Room t3 = new Room("Theater 3", 40);
            Room t4 = new Room("Theater 4", 40);
            Room t5 = new Room("Theater 5", 40);
            Room t6 = new Room("Theater 6", 40);

            roomRepo.saveAll(List.of(t1, t2, t3, t4, t5, t6));

            List.of(t1, t2, t3, t4, t5, t6).forEach(room -> {
                int totalSeats = room.getCapacity();
                int seatsPerRow = 8;

                for (int i = 0; i < totalSeats; i++) {
                    int rowIndex = i / seatsPerRow;
                    int colIndex = (i % seatsPerRow) + 1;

                    char rowChar = (char) ('A' + rowIndex);
                    String seatName = rowChar + String.valueOf(colIndex);

                    seatRepo.save(new Seat(seatName, room));
                }
            });

            List<Movie> movies = new ArrayList<>();

            movies.add(createMovie("Mad Max: Fury Road", 2015, "Action", "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler.", 14.00, 12, 0, 0, 2, t1));
            movies.add(createMovie("John Wick", 2014, "Action", "An ex-hitman comes out of retirement to track down the gangsters that took everything.", 13.50, 15, 30, 41, 1, t1));
            movies.add(createMovie("Top Gun: Maverick", 2022, "Action", "After thirty years, Maverick is still pushing the envelope as a top naval aviator.", 16.00, 18, 45, 10, 2, t1));
            movies.add(createMovie("Gladiator", 2000, "Action", "A former Roman General sets out to exact vengeance against the corrupt emperor.", 12.00, 21, 30, 35, 2, t1));

            movies.add(createMovie("Dune: Part Two", 2024, "Sci-Fi", "Paul Atreides unites with Chani and the Fremen while on a warpath of revenge.", 18.00, 11, 0, 46, 2, t2));
            movies.add(createMovie("Blade Runner 2049", 2017, "Sci-Fi", "A young blade runner's discovery of a long-buried secret leads him to track down Rick Deckard.", 15.00, 14, 15, 44, 2, t2));
            movies.add(createMovie("The Matrix", 1999, "Sci-Fi", "A computer hacker learns from mysterious rebels about the true nature of his reality.", 12.00, 17, 45, 16, 2, t2));
            movies.add(createMovie("Arrival", 2016, "Sci-Fi", "A linguist works with the military to communicate with alien lifeforms.", 13.00, 20, 30, 56, 1, t2));

            movies.add(createMovie("Spider-Verse", 2018, "Animation", "Teen Miles Morales becomes the Spider-Man of his universe.", 14.50, 10, 0, 57, 1, t3));
            movies.add(createMovie("The Lion King", 1994, "Animation", "A lion prince is cast out of his pride by his cruel uncle.", 11.00, 12, 15, 28, 1, t3));
            movies.add(createMovie("Spirited Away", 2001, "Animation", "A young girl wanders into a world ruled by gods, witches, and spirits.", 13.00, 14, 45, 5, 2, t3));
            movies.add(createMovie("Moana", 2016, "Animation", "An adventurous teenager sails out on a daring mission to save her people.", 12.00, 17, 30, 47, 1, t3));

            movies.add(createMovie("Hereditary", 2018, "Horror", "A grieving family is haunted by tragic and disturbing occurrences.", 14.00, 19, 0, 7, 2, t4));
            movies.add(createMovie("Scream", 1996, "Horror", "A year after the murder of her mother, a teenage girl is terrorized by a masked killer.", 12.00, 21, 15, 51, 1, t4));
            movies.add(createMovie("Psycho", 1960, "Horror", "A secretary embezzles money and checks into a remote motel run by a strange young man.", 11.00, 23, 0, 49, 1, t4));
            movies.add(createMovie("Alien", 1979, "Horror", "The crew of a commercial spacecraft encounter a deadly lifeform after investigating a transmission.", 13.00, 16, 30, 57, 1, t4));

            movies.add(createMovie("The Godfather", 1972, "Drama", "The aging patriarch of an organized crime dynasty transfers control to his reluctant son.", 15.00, 13, 0, 55, 2, t5));
            movies.add(createMovie("Parasite", 2019, "Drama", "Greed and class discrimination threaten the newly formed symbiotic relationship between families.", 14.50, 16, 15, 12, 2, t5));
            movies.add(createMovie("Oppenheimer", 2023, "Drama", "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.", 17.00, 19, 30, 0, 3, t5));
            movies.add(createMovie("The Shawshank Redemption", 1994, "Drama", "Two imprisoned men bond over a number of years, finding solace and eventual redemption.", 12.50, 10, 30, 22, 2, t5));

            movies.add(createMovie("Barbie", 2023, "Comedy", "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world.", 15.50, 12, 30, 54, 1, t6));
            movies.add(createMovie("Superbad", 2007, "Comedy", "Two co-dependent high school seniors are forced to deal with separation anxiety.", 11.00, 15, 0, 13, 1, t6));
            movies.add(createMovie("Mean Girls", 2004, "Comedy", "Cady Heron is a hit with the Plastics until she makes the mistake of falling for Aaron Samuels.", 10.50, 18, 0, 37, 1, t6));
            movies.add(createMovie("Everything Everywhere All At Once", 2022, "Sci-Fi", "A Chinese-American immigrant is swept up in an insane adventure.", 14.50, 20, 30, 19, 2, t6));

            movieRepo.saveAll(movies);
            System.out.println("--- [SYSTEM_SUCCESS]: 24 Assets Ingested Across 6 Units ---");
        };
    }

    private Movie createMovie(String title, int year, String genre, String desc, double cost, int showH, int showM, int runM, int runH, Room room) {
        Movie movie = new Movie(title, LocalDate.of(year, 1, 1), genre, desc, cost, showH, showM, runM, runH);
        movie.getRooms().add(room);
        return movie;
    }
}