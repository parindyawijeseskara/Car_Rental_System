package lk.ijse.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String licenseNo;
    private String nic;
    private String contactNo;
    private Integer userTypeId;

}
