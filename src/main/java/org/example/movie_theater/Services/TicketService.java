package org.example.movie_theater.Services;

import jakarta.transaction.Transactional;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.Repos.SeatRepository;
import org.example.movie_theater.Repos.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        ticket.setCost(seat.getRoom().getRoomCost());

        return ticketRepo.save(ticket);
    }
}
