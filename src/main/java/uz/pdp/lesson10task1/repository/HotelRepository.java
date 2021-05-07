package uz.pdp.lesson10task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lesson10task1.model.Hotel;
@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {
boolean existsByName(String nameHotel);
}
