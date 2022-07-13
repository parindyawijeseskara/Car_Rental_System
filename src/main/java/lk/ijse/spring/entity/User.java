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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
////
    @OneToOne
    @JoinColumn(name = "userTypeId",referencedColumnName = "userTypeId")
    private UserType userTypeId;

    private String userName;
    private String email;
    private String password;
    private String address;
    private String licenseNo;
    private String nic;
    private String contactNo;

}
