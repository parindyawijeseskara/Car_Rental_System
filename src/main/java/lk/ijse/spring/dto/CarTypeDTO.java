package lk.ijse.spring.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CarTypeDTO {
    private List<Integer> carId;
    private Date pickUpDate;
    private Date returnDate;
}
