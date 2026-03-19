package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String name;
    private Integer capacity;

    @ManyToMany(mappedBy = "rooms")
    @OrderBy("id ASC")
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("seatNumber ASC")
    private List<Seat> seats = new ArrayList<>();

    public Room(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}