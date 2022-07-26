package lk.ijse.spring.controller;


import lk.ijse.spring.dto.CarTypeDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.service.RentalRequestService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rental_request")
@CrossOrigin
public class RentalRequestController {

    @Autowired
    private RentalRequestService rentalRequestService;

    /** Save Rental Reuwsts */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveRentalRequest(@RequestBody RentalRequestDTO rentalRequestDTO){
        rentalRequestService.saveRentalRequest(rentalRequestDTO);
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
    @GetMapping(path = "/getLossDamageAmount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getLossDamageAmount(@RequestBody List<CarTypeDTO> carTypeDTO){
        return new ResponseUtil(200,"Loss Damage Payment", rentalRequestService.getLossDamageAmount(carTypeDTO));
    }


}
