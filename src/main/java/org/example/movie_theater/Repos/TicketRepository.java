package org.example.movie_theater.Repos;

import org.example.movie_theater.Entities.Ticket;
import org.example.movie_theater.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserAndStatus(User user, String status);
    long countByUserAndStatus(User user, String status);
    List<Ticket> findByUser(User user);
}