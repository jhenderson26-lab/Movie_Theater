package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Entity
@Table
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Getter
    private String roomNumber;

//    @Setter
//    @Getter
//    private Double roomCost;

    @Setter
    @Getter
    private Integer maxSeats;

    @Getter
    @Setter
    @ManyToMany @JoinTable(
        name = "room_movies",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats;


    public Room(){

    }
    public Room(Long id, String roomNumber, Integer maxSeats, List<Movie> movies){
        this.roomNumber = roomNumber;
        this.maxSeats = maxSeats;
        this.movies = movies;
    }
    public Room(String roomNumber, Integer maxSeats, List<Movie> movies){
        this.roomNumber = roomNumber;
        this.maxSeats = maxSeats;
        this.movies = movies;
    }
}
