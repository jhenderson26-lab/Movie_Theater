package org.example.movie_theater.Repos;

import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId ORDER BY s.id ASC")
    List<Seat> findByRoomId(Long roomId);

    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId ORDER BY s.id ASC")
    List<Seat> findByRoomAndIsOccupiedFalse(Room room);

    boolean existsBySeatNumberAndRoom(String seatNumber, Room room);
}