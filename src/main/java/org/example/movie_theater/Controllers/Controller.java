package org.example.movie_theater.Controllers;

import ch.qos.logback.core.model.Model;
import org.apache.catalina.connector.Response;
import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
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

    public Controller(MovieService movieService) {
        this.movieService = movieService;
    }

    // homepage mapping
    @GetMapping("/")
    public List<Room> Homepage(Model model) {
        return roomService.getAllRooms();
    }

    // show all seats in a specified room
    // when a room is clicked on in the room list
    @GetMapping("/{roomId}/seats")
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
}
