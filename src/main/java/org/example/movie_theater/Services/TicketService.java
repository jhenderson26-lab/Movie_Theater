package org.example.movie_theater.Services;

import jakarta.transaction.Transactional;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.Repos.SeatRepository;
import org.example.movie_theater.Repos.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired private TicketRepository ticketRepo;
    @Autowired private SeatRepository seatRepo;

    @Transactional
    public Ticket createTicketForSeat(Long seatId) {
        Seat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new RuntimeException("ERROR: Seat not found"));

        if (seat.getIsTaken()) {
            throw new IllegalStateException("ERROR: This seat is occupied");
        }

        seat.setIsTaken(true);
        seatRepo.save(seat);

        Ticket ticket = new Ticket();
        ticket.setSeat(seat);

        // get first from movies list for now,
        // in the future movies should have show times
        // which will determine what movie is used in the price calc
        ticket.setCost(seat.getRoom().getMovies().getFirst().getCost());

        return ticketRepo.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
}
