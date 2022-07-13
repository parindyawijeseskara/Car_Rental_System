package lk.ijse.spring.controller;


import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.service.DriverService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    //add drivers
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveDriver(@RequestBody DriverDTO driverDTO){
        driverService.saveDriver(driverDTO);
        return new ResponseUtil(200,"Saved",driverDTO);
    }

    //delete drivers
    @DeleteMapping(params = {"driverId"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteDriver(@RequestParam Integer driverId){
        driverService.deleteDriver(driverId);
        return new ResponseUtil(200,"Deleted Driver Successfully",null);
    }

    //update drivers
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateDriver(@RequestBody DriverDTO driverDTO){
        driverService.updateDriver(driverDTO);
        return new ResponseUtil(200,"Updated Car Successfully",driverDTO);
    }

    //search drivers
    @GetMapping(path = "/{driverId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchDriver(@PathVariable Integer driverId){
        DriverDTO driverDTO = driverService.searchDriver(driverId);
        return new ResponseUtil(200,"Ok",driverDTO);
    }

    //get all drivers
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllDrivers(){
        return new ResponseUtil(200,"Ok",driverService.getAllDrivers());
    }


}
