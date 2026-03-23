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

    // --- 1. GENERAL NAVIGATION ---

    @GetMapping("/")
    public String Homepage(Model model, Principal principal) {
        model.addAttribute("Movielist", movieService.getAllMovies());
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
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

    // --- 2. TICKET & CART SYSTEM ---

    @GetMapping("/TicketBooking/{movieId}")
    public String showSeatSelection(@PathVariable Long movieId, Model model) {
        Movie movie = movieService.findMovieById(movieId);
        List<Seat> allSeats = movie.getRooms().getFirst().getSeats();
        List<Long> occupiedSeatIds = ticketService.getOccupiedSeatIdsForMovie(movieId);

        model.addAttribute("movie", movie);
        model.addAttribute("seats", allSeats);
        model.addAttribute("occupiedSeatIds", occupiedSeatIds);
        model.addAttribute("isEditing", false);
        return "Movie/SeatSelection";
    }

    @PostMapping("/AddToCart")
    public String addToCart(@RequestParam(required = false) Long movieId,
                            @RequestParam(required = false) List<Long> seatIds,
                            Principal principal) {
        if (principal == null || movieId == null || seatIds == null || seatIds.isEmpty()) {
            return "redirect:/AllMovies";
        }
        User user = userService.findByUsername(principal.getName());
        Movie movie = movieService.findMovieById(movieId);
        for (Long seatId : seatIds) {
            ticketService.createTicketForSeat(seatId, user, movie);
        }
        return "redirect:/Cart";
    }

    @GetMapping("/Cart")
    public String viewCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Ticket> cartItems = ticketService.getCartByUser(user);
        model.addAttribute("cartItems", cartItems);
        return "Cart";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("cartItemIds") List<Long> cartItemIds) {
        if (cartItemIds != null) {
            for (Long ticketId : cartItemIds) {
                ticketService.purchaseTicket(ticketId);
            }
        }
        return "redirect:/MyTickets";
    }

    @GetMapping("/MyTickets")
    public String viewPurchasedTickets(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Ticket> tickets = ticketService.getPurchasedTickets(user);
        model.addAttribute("tickets", tickets);
        return "TicketHistory";
    }

    @GetMapping("/History")
    public String showHistory(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Ticket> history = ticketService.getPurchaseHistory(user);
        model.addAttribute("history", history);
        return "TicketHistory";
    }

    // --- 3. TICKET MODIFICATIONS ---

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
                               @RequestParam(name = "seatIds") List<Long> seatIds) {
        if (!seatIds.isEmpty()) {
            ticketService.updateTicketSeat(ticketId, seatIds.get(0));
        }
        return "redirect:/Cart";
    }

    @PostMapping("/Cart/Remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        ticketService.removeTicketFromCart(id);
        return "redirect:/Cart";
    }

    @PostMapping("/DeleteTicket/{id}")
    public String deleteTicket(@PathVariable("id") Long id) {
        ticketService.removeTicketFromCart(id);
        return "redirect:/MyTickets";
    }

    // --- 4. ROOM MANAGEMENT (ADMIN) ---

    @GetMapping("/AddingRoomPage")
    public String AddingRoom(Model model){
        model.addAttribute("Movielist", movieService.getAllMovies());
        return "Making/NewRoom";
    }

    @PostMapping("/AddedNewRoom")
    public String AddedRoom(@ModelAttribute Room room, @RequestParam(value = "movieIds", required = false) List<Long> movieIds) {
        if (room.getName() == null || room.getName().isEmpty()) {
            return "redirect:/AddingRoomPage";
        }
        room = roomService.addRoomR(room);
        if (movieIds != null) {
            for (Long id : movieIds) {
                Movie movie = movieService.findMovieById(id);
                if (movie != null) {
                    movie.getRooms().add(room);
                    movieService.saveMovie(movie);
                }
            }
        }
        addingSeat4Room(room);
        return "redirect:/Rooms";
    }

    @PostMapping("/EditPageRoom")
    public String EditingPageRoom(@RequestParam Long id, Model model){
        model.addAttribute("Movielist", movieService.getAllMovies());
        model.addAttribute("Roomlist", roomService.findRoomById(id));
        return "EditRoom";
    }

    @PostMapping("/UpdateRoom/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room updatedRoom, @RequestParam(value = "movieIds", required = false) List<Long> movieIds) {
        Room room = roomService.findRoomById(id);
        room.setName(updatedRoom.getName());
        room.setCapacity(updatedRoom.getCapacity());

        // Sync Relationships
        room.getMovies().forEach(m -> m.getRooms().remove(room));
        room.getMovies().clear();
        if (movieIds != null) {
            List<Movie> selectedMovies = movieService.findAllById(movieIds);
            selectedMovies.forEach(m -> {
                room.getMovies().add(m);
                m.getRooms().add(room);
            });
        }
        roomService.addRoom(room);
        return "redirect:/Rooms";
    }

    @PostMapping("/DeleteRoom/{id}")
    public String deleteRoom(@PathVariable Long id) {
        Room room = roomService.findRoomById(id);
        if (room != null) {
            room.getMovies().forEach(m -> m.getRooms().remove(room));
            room.getMovies().clear();
            roomService.deleteRoom(id);
        }
        return "redirect:/Rooms";
    }

    // --- 5. MOVIE MANAGEMENT (ADMIN) ---

    @GetMapping("/AddingMoviePage")
    public String AddingMovie(Model model){
        model.addAttribute("Roomlist", roomService.getAllRooms());
        return "Making/NewMovie";
    }

    @PostMapping("/AddedNewMovie")
    public String AddedMovie(@ModelAttribute("movie") Movie movie,
                             @RequestParam("room") Long roomId) {
        Room selectedRoom = roomService.findRoomById(roomId);
        if (selectedRoom != null) {
            movie.getRooms().add(selectedRoom);
        }
        movieService.saveMovie(movie);
        return "redirect:/AllMovies";
    }

    @PostMapping("/EditingMoviePage")
    public String EditMoviePage(@RequestParam Long id, Model model) {
        model.addAttribute("Movielist", movieService.findMovieById(id));
        model.addAttribute("Roomlist", roomService.getAllRooms());
        return "Movie/EditMovie";
    }

    @PostMapping("/UpdateMovie/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie updatedMovie) {
        Movie movie = movieService.findMovieById(id);
        movie.setTitle(updatedMovie.getTitle());
        movie.setReleaseYear(updatedMovie.getReleaseYear());
        movie.setGenre(updatedMovie.getGenre());
        movie.setDescription(updatedMovie.getDescription());
        movie.setCost(updatedMovie.getCost());
        movie.setShow_time_hour(updatedMovie.getShow_time_hour());
        movie.setShow_time_minute(updatedMovie.getShow_time_minute());
        movie.setRuntime_hour(updatedMovie.getRuntime_hour());
        movie.setRuntime_minute(updatedMovie.getRuntime_minute());
        movieService.saveMovie(movie);
        return "redirect:/AllMovies";
    }

    @DeleteMapping("/DeleteMovie")
    public String deleteMovie(@RequestParam("id") Long id) {
        movieService.deleteMovie(id);
        return "redirect:/AllMovies";
    }

    // --- 6. HELPERS ---

    public void addingSeat4Room(Room room){
        int capacity = room.getCapacity();
        int seatsPerRow = 8;
        int rows = (int) Math.ceil((double) capacity / seatsPerRow);
        roomService.initializeSeats(room.getId(), rows, seatsPerRow);
    }
}