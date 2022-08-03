package lk.ijse.spring.service.impl;


import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.DocumentTypeDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.*;
import lk.ijse.spring.repo.DocumentRepo;
import lk.ijse.spring.repo.DocumentTypeRepo;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.repo.UserTypeRepo;
import lk.ijse.spring.service.UserRegistrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private DocumentRepo documentRepo;


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
    public void saveUser(List<MultipartFile> fileList, UserDTO dto) {
//        User save = userRepo.save(mapper.map(dto, User.class));

        UserType userTypeId = userTypeRepo.findByUserTypeId(dto.getUserTypeId());
//        User userTypeId = userRepo.findByUserTypeId(dto.getUserTypeId());
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAddress(dto.getAddress());
        user.setLicenseNo(dto.getLicenseNo());
        user.setNic(dto.getNic());
        user.setContactNo(dto.getContactNo());
        user.setUserTypeId(userTypeId);

        userRepo.save(user);
        User userId = userRepo.findByUserId(dto.getUserId());

        //Get document type Id
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        DocumentType documentTypeId = documentTypeRepo.findByDocumentTypeId(documentTypeDTO.getDocumentTypeId());

        // document save
        if (!fileList.isEmpty()) {
            fileList.forEach((doc) -> {
                Document document = new Document();


                try {

                    int typeIndex = doc.getOriginalFilename().indexOf('/');
                    int extensionIndex = doc.getOriginalFilename().lastIndexOf('.');
                    String fileName = doc.getOriginalFilename().substring(typeIndex + 1, extensionIndex);

                    String  fileNameWithExtension = doc.getOriginalFilename().substring(typeIndex + 1);

                    document.setName(fileName);
                    document.setContent(doc.getBytes());
                    document.setUploadedBy(userId);
                    document.setUploadedOn(new Date());
                    document.setPaymentId(null);
                    document.setCarId(null);
                    document.setDocumentTypeId(documentTypeId);

                    documentRepo.save(document);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
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
        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user:users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setAddress(user.getAddress());
            userDTO.setLicenseNo(user.getLicenseNo());
            userDTO.setNic(user.getNic());
            userDTO.setContactNo(user.getContactNo());

            UserType byUserTypeId = userTypeRepo.findByUserTypeId(user.getUserTypeId().getUserTypeId());
            userDTO.setUserTypeId(byUserTypeId.getUserTypeId());
            userDTOS.add(userDTO);

        }
            return userDTOS;
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

    @Override
    public List<UserDTO> findAllByUserTypeIdUserTypeId(Integer userTypeId) {
        List<User> allByUserTypeIdUserTypeId = userRepo.findAllByUserTypeIdUserTypeId(userTypeId);
        List<UserDTO> userDTO = new ArrayList<>();

        for (User userType:allByUserTypeIdUserTypeId) {
            UserDTO userDTO1 = new UserDTO();
            userDTO1.setUserId(userType.getUserId());
            userDTO1.setUserName(userType.getUserName());
            userDTO1.setEmail(userType.getEmail());
            userDTO1.setPassword(userType.getPassword());
            userDTO1.setAddress(userType.getAddress());
            userDTO1.setLicenseNo(userType.getLicenseNo());
            userDTO1.setNic(userType.getNic());
            userDTO1.setContactNo(userType.getContactNo());
            userDTO1.setUserTypeId(userTypeId);
            userDTO.add(userDTO1);
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserInLogging(String userName, String password, String userTypeId) {
        User user = userRepo.findByUserNameAndPasswordAndUserTypeIdUserTypeId(userName, password, Integer.valueOf(userTypeId));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setUserTypeId(user.getUserTypeId().getUserTypeId());

//        if(user.isPresent()){
//            User user1 = user.get();
//            System.out.println(user1.toString());
//            return mapper.map(user1,UserDTO.class);
//        }
        return userDTO;
    }

    @Override
    public UserDTO getUserInLog(Integer userId) {
        User userId1 = userRepo.findByUserId(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userId1.getUserName());
        userDTO.setEmail(userId1.getEmail());
        userDTO.setPassword(userId1.getPassword());
        userDTO.setAddress(userId1.getAddress());
        userDTO.setLicenseNo(userId1.getLicenseNo());
        userDTO.setNic(userId1.getNic());
        userDTO.setContactNo(userId1.getContactNo());

        return userDTO;
    }

}
