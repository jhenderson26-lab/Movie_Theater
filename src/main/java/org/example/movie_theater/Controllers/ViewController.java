package org.example.movie_theater.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.ui.Model;

// 3. Dependency Injection & Responses
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.Services.MovieService;
import org.example.movie_theater.Services.RoomService;
import org.example.movie_theater.Services.TicketService;

// 5. Java Utilities
import java.util.List;
@Controller
public class ViewController {

    private final MovieService movieService;

    @Autowired
    public ViewController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/")
    public String index(Model model){
        return "index";
    }


    @GetMapping("/AllMovies")
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();

        model.addAttribute("Movielist", movies);
        return "Movie/AllMovies";
    }
}
