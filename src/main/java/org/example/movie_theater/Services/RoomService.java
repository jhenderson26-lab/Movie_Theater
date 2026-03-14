package org.example.movie_theater.Services;

import org.example.movie_theater.Entities.Room;
import org.example.movie_theater.Entities.Seat;
import org.example.movie_theater.Repos.RoomRepository;
import org.example.movie_theater.Repos.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Seat> getSeatsByRoomId(long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalStateException("ERROR: Room with id " + roomId + " does not exist");
        }
        return seatRepository.findByRoomId(roomId);
    }

    public Room GettingRoom(Long id){
        if (!roomRepository.existsById(id)){
            throw new IllegalStateException("ERROR: Room with id " + id + " does not exist");
        }
        return roomRepository.GetRoom(id);
    }

    public Long seatsAmounts(Long id){
        return seatRepository.getSeatCountByRoom(id);
    }

    public Room createRoom(Room room) {
        Room savedRoom = roomRepository.save(room);
        populateRoomWithSeats(savedRoom);
        return savedRoom;
    }

    public void populateRoomWithSeats(Room room) {
        if (room.getMaxSeats() > seatsAmounts(room.getId())){
            for (int i = 1; i <= room.getMaxSeats(); i++) {
                Seat seat = new Seat();
                seat.setSeatNumber("S" + i);
                seat.setIsTaken(false);
                seat.setRoom(room);
                seatRepository.save(seat);
            }
        }
    }
}
