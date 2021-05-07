package uz.pdp.lesson10task1.payload;

import lombok.Data;

@Data
public class RoomDTO {
    private Integer number,floor,size;
    private Integer hotelId;
}
