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
    private String seatNumber; // e.g., "A1", "B5"

    @Setter
    @Column(nullable = false)
    private boolean isOccupied = false;

    /**
     * Many seats belong to one room.
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Helper constructor for seeding data
    public Seat(String seatNumber, Room room) {
        this.seatNumber = seatNumber;
        this.room = room;
        this.isOccupied = false;
    }
}