package org.example.movie_theater.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
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
    private Float cost;

    @ManyToMany(mappedBy = "movies")
    private List<Room> rooms;

    public Movie() {}

    public Movie(Long id, String title, LocalDate releaseYear, String genre, String description, Float cost) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.cost = cost;
    }

    public Movie(String title, LocalDate releaseYear, String genre, String description, Float cost) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}


