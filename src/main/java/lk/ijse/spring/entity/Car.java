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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carId;

//    @OneToMany
//    @JoinColumn(name = "documentId",referencedColumnName = "documentId")
//    private List<Document> documentId;

    private int registerNo;
    private String brand;
    private String type;
    private String colour;
    private String maintainence;
    private Date createdOn;
    private String transmissionType;
    private Date updatedOn;
    @ManyToOne
    @JoinColumn(name = "updatedBy",referencedColumnName = "userId")
    private User updatedBy;
    private String status;
    private String freeKmPerDay;
    private String freeKmPerMonth;
    private double monthlyRate;
    private double dailyRate;
    private double pricePerExtraKm;
    private String noOfPassengers;
    private String fuelType;

    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "userId")
    private User createdBy;




}
