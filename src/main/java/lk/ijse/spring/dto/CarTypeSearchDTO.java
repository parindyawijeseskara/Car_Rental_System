package lk.ijse.spring.dto;

import lombok.Data;

@Data
public class CarTypeSearchDTO {
    private String noOfPessangers;
    private String transmission;
    private String brand;
    private String type;
    private String fuelType;
}
