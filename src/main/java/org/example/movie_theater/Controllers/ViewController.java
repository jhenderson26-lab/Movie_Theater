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
import org.springframework.web.client.HttpClientErrorException;

// 5. Java Utilities
import java.util.List;
@Controller
public class ViewController {

    private final MovieService movieService;
    private final RoomService roomService;

    @Autowired
    public ViewController(MovieService movieService, RoomService roomService) {
        this.movieService = movieService;
        this.roomService = roomService;
    }


    @GetMapping("/")
    public String Homepage(Model model){
        return "index";
    }


    @GetMapping("/AllMovies")
    public String AllMoviesPage(Model model) {
        List<Movie> movies = movieService.getAllMovies();

        model.addAttribute("Movielist", movies);
        return "Movie/AllMovies";
    }

    @GetMapping("/Rooms")
    public String RoomsPage(Model model) {
        List<Room> rooms = roomService.getAllRooms();

        model.addAttribute("Roomlist", rooms);
        return "Room";
    }

    @GetMapping("rooms/{id}/seats")
    public String getRoomSeats(@PathVariable("id") Long roomId, Model model) {
        Room TheRoom = roomService.GettingRoom(roomId);
        roomService.populateRoomWithSeats(TheRoom);

        Long amount = roomService.seatsAmounts(roomId);
        model.addAttribute("seatCount", amount);

        List<Seat> seats = roomService.getSeatsByRoomId(roomId);
        model.addAttribute("RoomSeats", seats);
        return "Seats";
    }

    @GetMapping("/MoviePage/{movieId}")
    public String MoviePage(Model model, @PathVariable String movieId) {
        // do this manual conversion to long from string
        // minimizes implicit conversions
        long idL;
        try {
            idL = Long.parseLong(movieId);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: '" + movieId + "' is not a valid ID.");
            return "Movie/MoviePage";
        }

        Movie selected_movie = movieService.findMovieById(idL);
        model.addAttribute("movie", selected_movie);
        return "Movie/MoviePage";
    }
}
