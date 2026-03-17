//test commit
package org.example.movie_theater.Controllers;

import org.example.movie_theater.Entities.*;
import org.example.movie_theater.Services.*;
import org.example.movie_theater.User.User;
import org.example.movie_theater.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ViewController {
    private final MovieService movieService;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public ViewController(MovieService movieService,
                          RoomService roomService,
                          TicketService ticketService,
                          UserService userService) {
        this.movieService = movieService;
        this.roomService = roomService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    // basic navigation endpoints

    @GetMapping("/")
    public String Homepage() {
        return "index";
    }

    @GetMapping("/AllMovies")
    public String AllMoviesPage(Model model) {
        model.addAttribute("Movielist", movieService.getAllMovies());
        return "Movie/AllMovies";
    }

    @GetMapping("/MoviePage/{movieId}")
    public String MoviePage(@PathVariable Long movieId, Model model) {
        Movie selected_movie = movieService.findMovieById(movieId);
        model.addAttribute("movie", selected_movie);
        return "Movie/MoviePage";
    }

    @GetMapping("/Rooms")
    public String RoomsPage(Model model) {
        model.addAttribute("Roomlist", roomService.getAllRooms());
        return "Room";
    }


    // ticket related endpoints

    @GetMapping("/TicketBooking/{movieId}")
    public String showSeatSelection(@PathVariable Long movieId, Model model) {
        Movie movie = movieService.findMovieById(movieId);
        // debug
        System.out.println(movie.getTitle());
        System.out.println(movie.getRooms().size());

        if (movie.getRooms() == null || movie.getRooms().isEmpty()) {
            System.out.println("REDIRECTING: No rooms found for this movie.");
            return "redirect:/AllMovies";
        }

        List<Seat> seats = movie.getRooms().getFirst().getSeats();
        // debug
        System.out.println((seats != null ? seats.size() : 0));

        if (seats == null || seats.isEmpty()) {
            System.out.println("REDIRECTING: Room has no seats initialized.");
            return "redirect:/AllMovies";
        }

        model.addAttribute("movie", movie);
        model.addAttribute("seats", seats);
        model.addAttribute("isEditing", false);
        return "Movie/SeatSelection";
    }

    @PostMapping("/AddToCart")
    public String addToCart(@RequestParam(required = false) Long movieId,
                            @RequestParam(required = false) Long seatId,
                            Principal principal) {
        // principal is the logged-in user session
        User user = userService.findByUsername(principal.getName());

        if (movieId == null || seatId == null || principal == null){
            return "redirect:/Cart";
        }
        Movie movie = movieService.findMovieById(movieId);
        ticketService.createTicketForSeat(seatId, user, movie);
        return "redirect:/Cart";
    }


    @GetMapping("/Cart")
    public String viewCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Ticket> cartItems = ticketService.getCartByUser(user);

        model.addAttribute("cartItems", cartItems);
        return "Cart";
    }

    @GetMapping("/EditTicket/{ticketId}")
    public String editTicket(@PathVariable Long ticketId, Model model) {
        Ticket ticket = ticketService.findTicketById(ticketId);

        model.addAttribute("movie", ticket.getMovie());
        model.addAttribute("seats", ticket.getMovie().getRooms().getFirst().getSeats());
        model.addAttribute("existingTicketId", ticketId);
        model.addAttribute("isEditing", true);
        return "Movie/SeatSelection";
    }

    @PostMapping("/UpdateTicket")
    public String updateTicket(@RequestParam Long ticketId,
                               @RequestParam Long newSeatId) {
        ticketService.updateTicketSeat(ticketId, newSeatId);
        return "redirect:/Cart";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("cartItemIds") List<Long> cartItemIds, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        for (Long ticketId : cartItemIds) {
            ticketService.purchaseTicket(ticketId);
        }
        return "redirect:/MyTickets";
//        List<Ticket> cartItems = ticketService.getCartByUser(user);
//
//        for (Ticket item : cartItems) {
//            System.out.println("Ticket ID: " + item.getId());
//
//            if (item.getSeat() != null) {
//                System.out.println("Seat Number: " + item.getSeat().getSeatNumber());
//            }
//        }
//
//        return "redirect:/Cart";
    }

    @GetMapping("/MyTickets")
    public String viewPurchasedTickets(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Ticket> tickets = ticketService.getPurchasedTickets(user);
        model.addAttribute("tickets", tickets);
        return "Tickets/MyTickets";
    }

    @PostMapping("/AddingRoomPage")
    public String AddingRoom(Model model){
        model.addAttribute("Movielist", movieService.getAllMovies());
        return "Making/NewRoom";
    }

    @PostMapping("/AddedNewRoom")
    public String AddedRoom(@ModelAttribute Room room, @RequestParam(value = "movies", required = false) Long movieId) {
        if (room.getName() == null || room.getName().isEmpty()) {
            return "redirect:/AddingRoomPage";
        }
        if (movieId != null) {
            Movie movie = movieService.findMovieById(movieId);
            movie.getRooms().add(room);
        }
        roomService.addRoom(room);

        int seatsPerRow = (int) Math.sqrt(room.getCapacity());
        int rows = (int) Math.ceil((double) room.getCapacity() / seatsPerRow);

        if (rows <= seatsPerRow) {

            seatsPerRow--;
            rows = (int) Math.ceil((double) room.getCapacity() / seatsPerRow);
        }
        Long NewRoom = room.getId();
        roomService.initializeSeats(NewRoom, rows, seatsPerRow);

        return "Movie/AllMovies";
    }

    @PostMapping("/AddingMoviePage")
    public String AddingMovie(Model model){
        model.addAttribute("Roomlist", roomService.getAllRooms());
        return "Making/NewMovie";
    }

    @PostMapping("/AddedNewMovie")
    public String AddedMovie(@ModelAttribute Movie movie, Model model, @RequestParam(value = "room", required = false) Long roomId){
        if(movie.getTitle() == null || movie.getTitle().isEmpty()){
            return "redirect:/NewMovie";
        }
        if (roomId != null) {
            Room room = roomService.findRoomById(roomId);
            movie.getRooms().add(room);
        }
        movieService.saveMovie(movie);
        return "Movie/AllMovies";
    }
}