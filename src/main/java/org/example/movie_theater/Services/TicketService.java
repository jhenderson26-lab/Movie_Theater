package org.example.movie_theater.Services;

import jakarta.transaction.Transactional;
import org.example.movie_theater.Entities.Movie;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.Repos.SeatRepository;
import org.example.movie_theater.Repos.TicketRepository;
import org.example.movie_theater.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired private TicketRepository ticketRepository;
    @Autowired private SeatRepository seatRepository;

    public List<Ticket> getCartByUser(User user) {
        return ticketRepository.findByUserAndStatus(user, "IN_CART");
    }
    public List<Ticket> getPurchasedTickets(User user) {
        return ticketRepository.findByUserAndStatus(user, "PURCHASED!");
    }

    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Transactional
    public void createTicketForSeat(Long seatId, User user, Movie movie) {
        if (ticketRepository.existsByMovieIdAndSeatId(movie.getId(), seatId)) {
            throw new RuntimeException("This seat is already reserved for this showing.");
        }

        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovie(movie);
        ticket.setSeat(seat);
        ticket.setCost(movie.getCost());
        ticket.setStatus("IN_CART");

        ticketRepository.save(ticket);
    }

    @Transactional
    public void updateTicketSeat(Long ticketId, Long newSeatId) {
        Ticket ticket = findTicketById(ticketId);

        boolean isTaken = ticketRepository.existsByMovieIdAndSeatId(
                ticket.getMovie().getId(),
                newSeatId
        );

        if (isTaken) {
            throw new RuntimeException("That seat is already reserved for this movie!");
        }

        Seat newSeat = seatRepository.findById(newSeatId).orElseThrow(() -> new RuntimeException("New seat not found"));

        ticket.setSeat(newSeat);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void removeTicketFromCart(Long ticketId) {
        Ticket ticket = findTicketById(ticketId);
        ticketRepository.delete(ticket);
    }

    @Transactional
    public void purchaseTicket(Long ticketId) {
        Ticket ticket = findTicketById(ticketId);
        ticket.setStatus("PURCHASED!");
        ticketRepository.save(ticket);
    }

    public List<Long> getOccupiedSeatIdsForMovie(Long movieId) {
        return ticketRepository.findByMovieId(movieId)
                .stream()
                .map(ticket -> ticket.getSeat().getId())
                .toList();
    }

    public List<Ticket> getPurchaseHistory(User user) {
        return ticketRepository.findByUserAndStatus(user, "PURCHASED");
    }

    @Transactional
    public void checkout(User user) {
        List<Ticket> cartItems = ticketRepository.findByUserAndStatus(user, "IN_CART");

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Your cart is empty!");
        }

        for (Ticket ticket : cartItems) {
            ticket.setStatus("PURCHASED");
        }

        ticketRepository.saveAll(cartItems);
    }
}