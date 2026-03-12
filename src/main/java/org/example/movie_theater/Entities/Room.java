package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Getter
    private String roomNumber;

    @Setter
    @Getter
    private Double roomCost;

    @Setter
    @Getter
    private Integer maxSeats;

    @ManyToMany @JoinTable(
        name = "room_movies",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
