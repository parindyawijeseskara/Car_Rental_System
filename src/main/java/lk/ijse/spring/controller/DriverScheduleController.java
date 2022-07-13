package lk.ijse.spring.controller;


import lk.ijse.spring.dto.DriverScheduleDTO;
import lk.ijse.spring.service.DriverScheduleService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/driver_schedule")
@CrossOrigin
public class DriverScheduleController {

    @Autowired
    private DriverScheduleService scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveDriverSchedule(@RequestBody DriverScheduleDTO driverScheduleDTO){
        scheduleService.saveDriverSchedule(driverScheduleDTO);
        return new ResponseUtil(200,"Ok",driverScheduleDTO);
    }

//    @DeleteMapping()
//    public ResponseUtil deleteDriverSchedule(@PathVariable Integer driverScheduleId){
//
//    }


}
