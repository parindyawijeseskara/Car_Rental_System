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
public class DriverSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer driverScheduleId;

    @OneToOne
    @JoinColumn(name = "driverId",referencedColumnName = "driverId")
    private Driver driverId;

    private Date endDate;
    private Date startDate;

}
