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
public class PaymentDTO {
    private int paymentId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double amount;
    private RentalRequestDTO rentalRequest;
    private UserDTO user;
    private double totalAmount;
    private Integer userId;
    private Integer rentalRequestId;
    private String userName;

}
