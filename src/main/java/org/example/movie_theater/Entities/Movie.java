package org.example.movie_theater.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Movie {
    @Getter
    @Setter
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

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private LocalDate releaseYear;

    @Setter
    @Getter
    private String genre;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Double cost;

    @Setter
    @Getter
    private Integer show_time_minute;

    @Setter
    @Getter
    private Integer show_time_hour;

    @ManyToMany(mappedBy = "movies")
    private List<Room> rooms;

    public Movie() {}

    public Movie(Long id, String title, LocalDate releaseYear, String genre, String description, Double cost, Integer show_time_minute, Integer show_time_hour) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.cost = cost;
        this.show_time_minute = show_time_minute;
        this.show_time_hour = show_time_hour;
    }

    public Movie(String title, LocalDate releaseYear, String genre, String description, Double cost, Integer show_time_minute, Integer show_time_hour) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.cost = cost;
        this.show_time_minute = show_time_minute;
        this.show_time_hour = show_time_hour;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", cost='" + cost +
                ", show_time_minute='" + show_time_minute + '\''+
                ", show_time_hour='" + show_time_hour + '\''+
                '}';
    }
}


