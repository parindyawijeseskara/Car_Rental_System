package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@IdClass(CarRentalRequest_PK.class)
public class CarRentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carRentalRequestId;

    @ManyToOne
    @JoinColumn(name = "carId",referencedColumnName = "carId",insertable = false,updatable = false)
    private Car carId;

    @ManyToOne
    @JoinColumn(name = "rentalRequestId",referencedColumnName = "rentalRequestId",insertable = false,updatable = false)
    private RentalRequest rentalRequestId;

    private double lossDamagePayment;

}



