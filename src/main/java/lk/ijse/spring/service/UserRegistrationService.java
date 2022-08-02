package lk.ijse.spring.service;

import lk.ijse.spring.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserRegistrationService {

    public void saveUser(List<MultipartFile> fileList, UserDTO dto);

    public void deleteUser(Integer userId);

    public void updateUser(UserDTO dto);

    public UserDTO searchUser(Integer userId);

    List<UserDTO>getAllUsers();

    //login
    public UserDTO getUser(String userName,String password);

    List<UserDTO>findAllByUserTypeIdUserTypeId(Integer userTypeId);

    public UserDTO getUserInLogging(String userName,String password,Integer userTypeId);


}
