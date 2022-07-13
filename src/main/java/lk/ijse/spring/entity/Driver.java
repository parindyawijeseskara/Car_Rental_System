package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer driverId;

    //
    @ManyToOne
    @JoinColumn(name = "createdBy",referencedColumnName = "userId")
    private User createdBy;

    private String name;
    private String nic;
    private String status;
    private String contactNo;
    private String driverLicense;
    private Date createdOn;
    private Date updatedOn;
    @ManyToOne
    @JoinColumn(name = "updatedBy",referencedColumnName = "userId")
    private User updatedBy;

}
