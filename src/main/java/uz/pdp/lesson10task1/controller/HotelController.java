package uz.pdp.lesson10task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson10task1.model.Hotel;
import uz.pdp.lesson10task1.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel hotelOnePageable(@PathVariable Integer id) {
        Optional<Hotel> byId1 = hotelRepository.findById(id);
        if (!byId1.isPresent()) {
            return new Hotel();
        }
        return byId1.get();
    }


    @PostMapping("/add")
    public String addHotel(@RequestBody Hotel newHotel) {
        Hotel hotel = new Hotel();
        if (hotelRepository.existsByName(newHotel.getName())) return "This hotel is already exist";
        hotel.setName(newHotel.getName());
        hotelRepository.save(hotel);
        return "success";
    }

    @PutMapping("/edit/{hotelId}")
    public String setHotel(@PathVariable Integer hotelId, @RequestBody Hotel newHotel) {
        Optional<Hotel> byId = hotelRepository.findById(hotelId);
        if (byId.isPresent()) {
            if (hotelRepository.existsByName(newHotel.getName())) {
                return "Already exist";
            }
            Hotel hotel = byId.get();
            hotel.setName(newHotel.getName());
            hotelRepository.save(hotel);
            return "Hotel edited successfully";
        }
        return "not found this id";

    }

    @DeleteMapping("/del/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        if (hotelRepository.findById(id).isPresent()) {
            hotelRepository.deleteById(id);
            return "successfully deleted";
        }
        return "hotel not found";
    }

}

