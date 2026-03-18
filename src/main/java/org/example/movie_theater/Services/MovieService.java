package org.example.movie_theater.Services;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie with ID " + id + " not found."));
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<Movie> findAllById(List<Long> movieIds) {
        List<Movie> movies = movieRepository.findAllById(movieIds);
        return movies;
    }
}