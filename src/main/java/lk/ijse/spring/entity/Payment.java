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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "rentalRequestId",referencedColumnName = "rentalRequestId")
    private RentalRequest rentalRequestId;

    @ManyToOne
    @JoinColumn(name = "paymentDoneBy",referencedColumnName = "userId")
    private User paymentDoneBy;

    private Date date;
    private double amount;

//    @OneToMany
//    @JoinColumn(name = "documentId",referencedColumnName = "documentId")
//    private List<Document>documentId;

}
