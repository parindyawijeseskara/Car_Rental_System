package lk.ijse.spring.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RentalRequestDTO {
    private int rentalRequestId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pickUpDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;
    private String status;
    private double lossDamagePayment;
    private String damageOrNot;
    private double rentalFee;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedOn;
    private int updatedBy;
    private int createdBy;
    private List<Integer> carId;
    private List<Integer> driverId;




}
