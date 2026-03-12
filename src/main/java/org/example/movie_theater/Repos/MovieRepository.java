package org.example.movie_theater.Repos;
import org.example.movie_theater.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleIsWithinIgnoreCase(String title);
}
