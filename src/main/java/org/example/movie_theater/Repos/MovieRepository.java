package org.example.movie_theater.Repos;
import org.example.movie_theater.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> { }
