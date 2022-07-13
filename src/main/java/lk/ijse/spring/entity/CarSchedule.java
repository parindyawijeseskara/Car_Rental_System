package lk.ijse.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class CarSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carScheduleId;

    @OneToOne
    @JoinColumn(name = "carId",referencedColumnName = "carId")
    private Car carId;

    private String brand;
    private Date startDate;
    private Date endDate;

}
