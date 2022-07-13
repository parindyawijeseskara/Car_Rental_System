package lk.ijse.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarRentalRequestDTO {
    private int carRentalRequestId;
    private int carId;
    private int rentalRequestId;
    private CarDTO car;
    private RentalRequestDTO rentalRequest;



}
