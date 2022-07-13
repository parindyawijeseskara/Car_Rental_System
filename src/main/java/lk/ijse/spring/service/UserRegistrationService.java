package lk.ijse.spring.service;

import lk.ijse.spring.dto.UserDTO;

import java.util.List;

public interface UserRegistrationService {

    public void saveUser(UserDTO dto);

    public void deleteUser(Integer userId);

    public void updateUser(UserDTO dto);

    public UserDTO searchUser(Integer userId);

    List<UserDTO>getAllUsers();

    //login
    public UserDTO getUser(String userName,String password);


}
