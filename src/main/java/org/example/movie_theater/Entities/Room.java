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
    private String name; // e.g., "Theater 1" or "IMAX Screen"
    private Integer capacity;

    @ManyToMany(mappedBy = "rooms")
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    // Helper constructor
    public Room(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}