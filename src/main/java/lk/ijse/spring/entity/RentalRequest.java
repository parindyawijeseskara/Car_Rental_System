package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class RentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentalRequestId;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "userId")
    private User createdBy;

    private Date createdOn;
    private Date pickUpDate;
    private Date returnDate;
    private String pickuptime;
    private String returntime;
    private String status;
    private String damageOrNot;
    private double rentalFee;
    private String comment;
    private Date updatedOn;
    private int updatedBy;


}
