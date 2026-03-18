package org.example.movie_theater.Repos;

import jakarta.transaction.Transactional;
import org.example.movie_theater.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Seat s WHERE s.id = :seatId AND s.room.id = :roomId")
    void deleteBySeatIdAndRoomId(@Param("seatId") Long seatId, @Param("roomId") Long roomId);
}