package uz.pdp.lesson10task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson10task1.payload.RoomDTO;
import uz.pdp.lesson10task1.model.Room;
import uz.pdp.lesson10task1.repository.HotelRepository;
import uz.pdp.lesson10task1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    @GetMapping
    public List<Room> findByAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/room/{id}")
    public Room getRoom(@PathVariable Integer id) {
        Optional<Room> byId = roomRepository.findById(id);
        if (!byId.isPresent()){
            return new Room();
        }
        return byId.get();
    }

    @GetMapping("/{id}")
    public Object getHotelWithPage(@RequestParam int page, @PathVariable Integer id) {
        if (!hotelRepository.findById(id).isPresent()) {
            return "There is not this id ): ";
        }
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(id, pageable);
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDTO roomDTO) {
        Room room1 = new Room();
        if (roomRepository.existsById(roomDTO.getId())&& roomRepository.existsByNumber(roomDTO.getNumber())) {
            return "qo`o`o`o`shoooomiysiz chunki sizzi shartizga to'ri keeelaaaadiiiii ):" +
                    "bu id bilan bu number allaqachon zaynit bo'gan";
        }
        room1.setNumber(roomDTO.getNumber());
        room1.setFloor(roomDTO.getFloor());
        room1.setSize(roomDTO.getSize());
        room1.setHotel(hotelRepository.getOne(roomDTO.getHotelId()));
        roomRepository.save(room1);
        return "barakalla qo'shildi :)";
    }

    @PutMapping("/edit/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> byId = roomRepository.findById(id);
        if (!byId.isPresent()) return "This id is not found";
        if (roomRepository.existsByNumber(roomDTO.getNumber())) return "Already exist ";
        Room room1 = byId.get();
        if (room1.getNumber().equals(roomDTO.getNumber())) return "edit qilomiysiz";
        if (room1.getFloor().equals(roomDTO.getFloor())) return "baribir edit qilomiysiz";
        room1.setSize(roomDTO.getSize());
        room1.setNumber(roomDTO.getNumber());
        room1.setFloor(roomDTO.getFloor());
        room1.setHotel(hotelRepository.getOne(roomDTO.getHotelId()));
        roomRepository.save(room1);
        return "barakalla o'zgartira oldiz :)";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        Optional<Room> byId = roomRepository.findById(id);
        if (!byId.isPresent()) return "There is not this id ):";
        Room room = byId.get();
        roomRepository.delete(room);
        return "successfully deleted room";
    }
}
