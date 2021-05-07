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
//    @GetMapping
//    public List<Hotel> getHotels(){
//        return hotelRepository.findAll();
//    }
//    @GetMapping("/{id}")
//    public Hotel getHotel(@PathVariable Integer id){
//        if (!hotelRepository.findById(id).isPresent()) return null;
//        Optional<Hotel> byId = hotelRepository.findById(id);
//        return byId.get();
//    }
//    @PostMapping("/adding")
//    public String addHotel(@RequestBody Hotel hotel){
////        if (hotelRepository.existsByNameHotel(hotel.getNameHotel())) return "This id already exist";
//        Hotel hotel1 = new Hotel();
//        hotel1.setNameHotel(hotel.getNameHotel());
//        hotelRepository.save(hotel1);
//        return "Successfully added Hotel";
//    }
//    @PutMapping("/edit/{id}")
//    public String editHotel(@PathVariable  Integer id,@RequestBody Hotel hotel){
//        Optional<Hotel> byId = hotelRepository.findById(id);
//        Hotel hotel1 = byId.get();
//        if (!byId.isPresent()) return "This id si not found";
//        if (hotelRepository.existsByNameHotel(hotel.getNameHotel())) return "This Id si already exist";
//        hotel1.setNameHotel(hotel.getNameHotel());
//        hotelRepository.save(hotel1);
//        return "Edited successfully";
//    }
//    @DeleteMapping("/delete/{id}")
//    public String deleteHotel(@PathVariable Integer id){
//        Optional<Hotel> byId = hotelRepository.findById(id);
//        if (!byId.isPresent()) return "This id not found ):";
//        Hotel hotel = byId.get();
//        hotelRepository.delete(hotel);
//        return "Successfully deleted";
//    }
@GetMapping
public List<Hotel> getHotels(){
    return hotelRepository.findAll();
}

    @GetMapping("/{id}")
    public Hotel hotelOnePageable(@PathVariable Integer id) {
        if (!hotelRepository.findById(id).isPresent()) {
            return null;
        }
        Optional<Hotel> byId = hotelRepository.findById(id);
        return  byId.get();
    }


    @PostMapping("/add")
    public String addHotel(@RequestBody Hotel newHotel) {
        for (Hotel hotel : hotelRepository.findAll()) {
            if (hotel.getName().equals(newHotel.getName())) {
                return "this hotel allready exist";
            }
        }
        hotelRepository.save(newHotel);
        return "success";
    }

    @PutMapping("/edit/{hotelId}")
    public String setHotel(@PathVariable Integer hotelId, @RequestBody Hotel newHotel) {
        Optional<Hotel> byId = hotelRepository.findById(hotelId);
        if (byId.isPresent()) {
            if (hotelRepository.existsByName(newHotel.getName())){
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
        if (hotelRepository.findById(id).isPresent()){
            hotelRepository.deleteById(id);
            return "successfully deleted";
        }
        return "hotel not found";
    }

}

