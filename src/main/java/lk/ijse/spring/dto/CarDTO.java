package lk.ijse.spring.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CarDTO {
    private int carId;
    private int registerNo;
    private String brand;
    private String type;
    private String colour;
    private String maintainence;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdOn;
    private String transmissionType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedOn;
    private int updatedBy;
    private String status;
    private String freeMileage;
    private double monthlyRate;
    private double dailyRate;
    private String extraKm;
    private String noOfPassengers;
    private String fuelType;
    private int createdBy;

}
