package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.example.movie_theater.User.User;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cost;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(Movie movie, Seat seat, User user, Double cost) {
        this.movie = movie;
        this.seat = seat;
        this.user = user;
        this.cost = cost;
        this.status = "IN_CART";
    }
}