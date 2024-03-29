package lk.ijse.spring.controller;


import com.google.gson.Gson;
import lk.ijse.spring.dto.CarTypeDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.service.RentalRequestService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/rental_request")
@CrossOrigin
public class RentalRequestController {

    @Autowired
    private RentalRequestService rentalRequestService;

    /** Save Rental Request */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseUtil saveRentalRequest(@RequestParam("file") List<MultipartFile> fileList, @RequestParam("model") String model){
        Gson gson = new Gson();
        RentalRequestDTO rentalRequestDTO = gson.fromJson(model, RentalRequestDTO.class);
        rentalRequestService.saveRentalRequest(fileList,rentalRequestDTO);
        return new ResponseUtil(200,"Saved",rentalRequestDTO);
    }

    /** Delete Rental Request */
    @DeleteMapping(params = {"rentalRequestId"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteRentalRequest(@RequestParam Integer rentalRequestId){
        rentalRequestService.deleteRentalRequest(rentalRequestId);
        return new ResponseUtil(200,"Deleted Rental Request Successfully",null);
    }

    /** Update Rental Request */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateRentalRequest(@RequestBody RentalRequestDTO rentalRequestDTO){
        rentalRequestService.updateRentalRequest(rentalRequestDTO);
        return new ResponseUtil(200,"Updated Rental Request Successfully",rentalRequestDTO);
    }

    /** Search Rental Request By Id*/
    @GetMapping(path = "/{rentalRequestId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchRentalRequest(@PathVariable Integer rentalRequestId){
        RentalRequestDTO rentalRequestDTO = rentalRequestService.searchRentalRequest(rentalRequestId);
        return new ResponseUtil(200,"Ok",rentalRequestDTO);
    }

    /** Get All Rental Request */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllRequests(){
        return new ResponseUtil(200,"Ok",rentalRequestService.getAllRequests());
    }

    /** Get All Pending Rental Request */
    @GetMapping(path = "/getAllPendingRequests",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllPendingRequests(){
        return new ResponseUtil(200,"Ok",rentalRequestService.getAllPendingRequests());
    }

    /** Get Loss Damage Payment */
    @PostMapping(path = "/getLossDamageAmount", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getLossDamageAmount(@RequestBody CarTypeDTO carTypeDTO){
        return new ResponseUtil(200,"Loss Damage Payment", rentalRequestService.getLossDamageAmount(carTypeDTO));
    }

    /** Get Rental Fee Payment */
    @PostMapping(path = "/getRentalFeeToPay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getRentalFeeToPay(@RequestBody CarTypeDTO carTypeDTO){
        return new ResponseUtil(200,"Rental Fee Payment", rentalRequestService.getRentalFeeToPay(carTypeDTO));
    }

}
