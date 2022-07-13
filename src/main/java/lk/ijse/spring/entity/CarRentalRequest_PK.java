package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarRentalRequest_PK implements Serializable {
    private int carRentalRequestId;
    private int carId;
    private int rentalRequestId;
}
