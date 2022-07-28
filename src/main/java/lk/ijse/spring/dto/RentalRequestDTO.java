package lk.ijse.spring.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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
    private String pickuptime;
    private String returntime;
    private String status;
    private String damageOrNot;
    private double rentalFee;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedOn;
    private int updatedBy;
    private int createdBy;
    private List<CarPaymentDTO> carPayment;
    private List<Integer> driverId;
    private List<String> carNameList;
    private List<String> driverNameList;
    private String nic;




}
