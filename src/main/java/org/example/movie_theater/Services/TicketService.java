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

    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    /**
     * Adds a ticket to the cart and locks the seat.
     */
    @Transactional
    public void createTicketForSeat(Long seatId, User user, Movie movie) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.isOccupied()) {
            throw new RuntimeException("Seat is already taken!");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovie(movie);
        ticket.setSeat(seat);
        ticket.setCost(movie.getCost());
        ticket.setStatus("IN_CART");

        // Mark seat as occupied so others can't grab it
        seat.setOccupied(true);
        seatRepository.save(seat);
        ticketRepository.save(ticket);
    }

    /**
     * Updates an existing ticket with a new seat selection.
     */
    @Transactional
    public void updateTicketSeat(Long ticketId, Long newSeatId) {
        Ticket ticket = findTicketById(ticketId);
        Seat oldSeat = ticket.getSeat();
        Seat newSeat = seatRepository.findById(newSeatId)
                .orElseThrow(() -> new RuntimeException("New seat not found"));

        if (newSeat.isOccupied()) {
            throw new RuntimeException("New seat is already taken!");
        }

        // 1. Swap occupancy
        oldSeat.setOccupied(false);
        newSeat.setOccupied(true);

        seatRepository.save(oldSeat);
        seatRepository.save(newSeat);

        // 2. Update ticket link
        ticket.setSeat(newSeat);
        ticketRepository.save(ticket);
    }

    /**
     * Optional: Logic to remove a ticket and free the seat.
     */
    @Transactional
    public void removeTicketFromCart(Long ticketId) {
        Ticket ticket = findTicketById(ticketId);
        Seat seat = ticket.getSeat();

        seat.setOccupied(false);
        seatRepository.save(seat);
        ticketRepository.delete(ticket);
    }
}