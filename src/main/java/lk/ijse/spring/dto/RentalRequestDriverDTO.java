package lk.ijse.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RentalRequestDriverDTO {
    private int rentalRequestDriverId;
    private int rentalRequestId;
    private int driverId;

}
