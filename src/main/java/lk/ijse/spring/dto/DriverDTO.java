package lk.ijse.spring.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DriverDTO {
    private int driverId;
    private String name;
    private String nic;
    private String status;
    private String contactNo;
    private String driverLicense;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedOn;
    private int updatedBy;
    private int createdBy;

}
