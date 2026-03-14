package org.example.movie_theater.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Setter
    @Column(nullable = false)
    private boolean isOccupied = false;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Seat(String seatNumber, Room room) {
        this.seatNumber = seatNumber;
        this.room = room;
        this.isOccupied = false;
    }
}