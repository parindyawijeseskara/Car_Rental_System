package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="documentId",unique = true, nullable = false)
    private Integer documentId;

    private String name;
    private byte[] content;
    private Date uploadedOn;

    //
    @ManyToOne
    @JoinColumn(name = "documentTypeId",referencedColumnName = "documentTypeId")
    private DocumentType documentTypeId;

    @ManyToOne
    @JoinColumn(name = "carId",referencedColumnName = "carId")
    private Car carId;

    @ManyToOne
    @JoinColumn(name = "paymentId",referencedColumnName = "paymentId")
    private Payment paymentId;

    @ManyToOne
    @JoinColumn(name = "uploadedBy",referencedColumnName = "userId")
    private User uploadedBy;

}
