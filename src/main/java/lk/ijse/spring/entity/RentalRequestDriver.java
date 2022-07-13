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
@IdClass(RentalRequestDriver_PK.class)
public class RentalRequestDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentalRequestDriverId;

    @ManyToOne
    @JoinColumn(name = "driverId",referencedColumnName = "driverId",insertable = false,updatable = false)
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "rentalRequestId",referencedColumnName = "rentalRequestId",insertable = false,updatable = false)
    private RentalRequest rentalRequestId;



}
