package org.example.movie_theater.Services;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Repos.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> AllMovie(){
        return movieRepository.findAll();
    }
}
