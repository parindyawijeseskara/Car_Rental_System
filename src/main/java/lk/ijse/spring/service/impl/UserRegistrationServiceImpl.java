package lk.ijse.spring.service.impl;


import lk.ijse.spring.dto.DocumentTypeDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.DocumentType;
import lk.ijse.spring.entity.User;
import lk.ijse.spring.entity.UserType;
import lk.ijse.spring.repo.DocumentTypeRepo;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.repo.UserTypeRepo;
import lk.ijse.spring.service.UserRegistrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private DocumentTypeRepo documentTypeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public void addUserType(){
        UserType userType1 = new UserType(1,"ADMIN");
        UserType userType2 = new UserType(2,"CUSTOMER");
        UserType userType3 = new UserType(3,"DRIVER");
        userTypeRepo.save(userType1);
        userTypeRepo.save(userType2);
        userTypeRepo.save(userType3);
    }

    @Autowired
    public void addDocumentType(){
        DocumentType d1 = new DocumentType(1,"Nic");
        DocumentType d2 = new DocumentType(2,"Driver License");
        DocumentType c1 = new DocumentType(3, "Car front view image");
        DocumentType c2 = new DocumentType(4,"Car back view image");
        DocumentType c3 = new DocumentType(5,"Car interior image");
        DocumentType c4 = new DocumentType(6,"Car side view image");
        documentTypeRepo.save(d1);
        documentTypeRepo.save(d2);
        documentTypeRepo.save(c1);
        documentTypeRepo.save(c2);
        documentTypeRepo.save(c3);
        documentTypeRepo.save(c4);
    }

    @Override
    public void saveUser(UserDTO dto) {
        userRepo.save(mapper.map(dto, User.class));

    }

    @Override
    public void deleteUser(Integer userId) {
        if(userRepo.existsById(userId)){
            userRepo.deleteById(userId);
        }else{
            throw new RuntimeException("Please check the User Id...No such User...!");
        }
    }

    @Override
    public void updateUser(UserDTO dto) {
        if (userRepo.existsById(dto.getUserId())){
            userRepo.save(mapper.map(dto,User.class));
        }else{
            throw new RuntimeException("No Such User To Update..! Please Check the ID..!");
        }
    }

    @Override
    public UserDTO searchUser(Integer userId) {
        if (userRepo.existsById(userId)){
            return mapper.map(userRepo.findById(userId).get(),UserDTO.class);
        }else{
            throw new RuntimeException("No such User for "+userId+"...!");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.map(userRepo.findAll(),new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    @Override
    public UserDTO getUser(String userName, String password) {
        Optional<User> user =userRepo.findByUserNameAndPassword(userName,password);
        if(user.isPresent()){
            User user1 = user.get();
            System.out.println(user1.toString());
            return mapper.map(user1,UserDTO.class);
        }
        return null;
    }
}
