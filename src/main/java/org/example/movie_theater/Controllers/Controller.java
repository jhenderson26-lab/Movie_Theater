package org.example.movie_theater.Controllers;

import ch.qos.logback.core.model.Model;
//import org.springframework.ui.Model;
import org.apache.catalina.connector.Response;
import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.Repos.MovieRepository;
import org.example.movie_theater.Services.MovieService;
import org.example.movie_theater.Services.RoomService;
import org.example.movie_theater.Services.TicketService;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class Controller {
    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RoomService roomService;
 //   @Autowired
   // private MovieRepository movieRepository;

    public Controller(MovieService movieService) {
        this.movieService = movieService;
    }

    // homepage mapping
    @GetMapping("/rooms")
    public List<Room> Homepage(Model model) {
        return roomService.getAllRooms();
    }

    // show all seats in a specified room
    // when a room is clicked on in the room list
    @GetMapping("/rooms/{roomId}/seats")
    public List<Seat> RoomSeats(Model model, @PathVariable String roomId) {
        long idL;
        try {
            idL = Long.parseLong(roomId);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: '" + roomId + "' is not a valid ID.");
            return List.of();
        }
        return roomService.getSeatsByRoomId(idL);
    }

    // post for booking a seat by ID
    @PostMapping("tickets/book/{seatId}")
    public ResponseEntity<Ticket> bookTicket(@PathVariable String seatId) {
        long idL;
        try {
            idL = Long.parseLong(seatId);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: '" + seatId + "' is not a valid ID.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ticketService.createTicketForSeat(idL));
    }

    // find movie by id
    @GetMapping("/movies/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.findMoviesByTitle(title));
    }

    @GetMapping("/AllMovies")
    public String getAllMovies() {
//        Iterable<Movie> movies = movieService.getAllMovies();
//        model.addAttribute("Movielist", movies);
        return "Movie/AllMovies";
    }

    // get all saved tickets
    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}
