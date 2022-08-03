package lk.ijse.spring.controller;


import com.google.gson.Gson;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.dto.UserLoginDTO;
import lk.ijse.spring.repo.UserTypeRepo;
import lk.ijse.spring.service.UserRegistrationService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/user_registration")
@CrossOrigin
public class UserRegistrationController {

    @Autowired
    UserRegistrationService userRegistrationService;

    //add User
    @ResponseStatus(HttpStatus.CREATED)  //get 201 status code in browser for successfully saved customer
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil saveUser(@RequestParam("file") List<MultipartFile> fileList, @RequestParam("model") String model){
        System.out.println(fileList.size());
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(model, UserDTO.class);
        userRegistrationService.saveUser(fileList,userDTO);
        return new ResponseUtil(200,"Saved",null);
    }

    //Login by userName and password
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(params = {"userName","password"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getUser(String userName, String password){
        UserDTO user = userRegistrationService.getUser(userName,password);
        return new ResponseUtil(200,"Done",user);
    }
    //Login by userName,password,userType in admin dashBoard
    @PostMapping(path = "/getUserInLogging" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getUserInLogging(@RequestBody UserLoginDTO userLoginDTO){
        UserDTO userInLogging = userRegistrationService.getUserInLogging(userLoginDTO.getUserName(),userLoginDTO.getPassword(),userLoginDTO.getUserTypeId());
        return new ResponseUtil(200,"Done",userInLogging);
    }

    @GetMapping(path = "/getUserByUserId/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getUserInLog(@PathVariable Integer userId){
        UserDTO userInLog = userRegistrationService.getUserInLog(userId);
        return new ResponseUtil(200,"Done",userInLog);

    }

    //search User
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(path = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchUser(@PathVariable Integer userId){
        UserDTO user = userRegistrationService.searchUser(userId);
        return new ResponseUtil(200,"Ok",user);
    }

    //delete User
    @DeleteMapping(params = {"userId"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteUser(@RequestParam Integer userId){
        userRegistrationService.deleteUser(userId);
        return new ResponseUtil(200,"Deleted User Successfully",null);
    }

    //updateUser
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateUser(@RequestBody UserDTO dto){
        userRegistrationService.updateUser(dto);
        return new ResponseUtil(200,"Updated User Successfully",null);
    }

    //get all users
    @GetMapping(path = "/getAllUsers" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllUsers(){
        return new ResponseUtil(200,"Ok",userRegistrationService.getAllUsers());
    }

    @GetMapping(path = "/searchByUserTypeId/{userTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findAllByUserTypeIdUserTypeId(@PathVariable Integer userTypeId){
        return new ResponseUtil(200,"Ok",userRegistrationService.findAllByUserTypeIdUserTypeId(userTypeId));
    }
}
