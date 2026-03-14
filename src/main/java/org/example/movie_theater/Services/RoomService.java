package org.example.movie_theater.Services;

import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Repos.RoomRepository;
import org.example.movie_theater.Repos.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    public void initializeSeats(Long roomId, int rows, int seatsPerRow) {
        Room room = findRoomById(roomId);
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            char rowLetter = (char) ('A' + i);
            for (int j = 1; j <= seatsPerRow; j++) {
                Seat seat = new Seat(rowLetter + String.valueOf(j), room);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }
}