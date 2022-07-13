package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalRequestDriver_PK implements Serializable {
    private int rentalRequestDriverId;
    private int rentalRequestId;
    private int driverId;
}
