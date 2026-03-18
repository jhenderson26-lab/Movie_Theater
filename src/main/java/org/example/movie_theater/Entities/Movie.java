package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    private Long id;

    private String title;
    private LocalDate releaseYear;
    private String genre;

    @Column(length = 1000)
    private String description;

    private Double cost;
    private Integer show_time_minute;
    private Integer show_time_hour;

    private Integer runtime_minute;
    private Integer runtime_hour;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_rooms",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Movie(String title, LocalDate releaseYear, String genre, String description, Double cost, Integer show_time_hour, Integer show_time_minute, Integer runtime_minute, Integer runtime_hour) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.cost = cost;
        this.show_time_hour = show_time_hour;
        this.show_time_minute = show_time_minute;
        this.runtime_minute = runtime_minute;
        this.runtime_hour = runtime_hour;
    }
}