package lk.ijse.spring.controller;


import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.service.CarService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/car")
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    //add cars
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveCar(@RequestBody CarDTO carDTO){
        carService.saveCar(carDTO);
        return new ResponseUtil(200,"Saved",carDTO);
    }

    //search cars
    @GetMapping(path = "/{carId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchCar(@PathVariable Integer carId){
        CarDTO carDTO = carService.searchCar(carId);
        return new ResponseUtil(200,"Ok",carDTO);
    }

    //delete cars
    @DeleteMapping(params = {"carId"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteCar(@RequestParam Integer carId){
        carService.deleteCar(carId);
        return new ResponseUtil(200,"Deleted Car Successfully",null);
    }

    //update cars
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateCar(@RequestBody CarDTO carDTO){
        carService.updateCar(carDTO);
        return new ResponseUtil(200,"Updated Car Successfully",carDTO);
    }

    //getAll cars
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllCars(){
        return new ResponseUtil(200,"Ok",carService.getAllCars());
    }

    //find cars by no of passengers
    @ResponseStatus(HttpStatus.CREATED)
    //@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @GetMapping(path = "/searchByPassenger/{noOfPassengers}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByNoOfPassengers(@PathVariable String noOfPassengers){
        return new ResponseUtil(200,"Ok",carService.findByNoOfPassengers(noOfPassengers));
    }

    //find cars by transmission type
    @GetMapping(path = "/searchByTransmission/{transmissionType}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByTransmissionType(@PathVariable String transmissionType){
        return new ResponseUtil(200,"Ok",carService.findByTransmissionType(transmissionType));
    }

    //find cars by brand
    @GetMapping(path = "/searchByBrand/{brand}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByBrand(@PathVariable String brand){
        return new ResponseUtil(200,"Ok",carService.findByBrand(brand));
    }

    //find cars by type
    @GetMapping(path = "/searchByType/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByType(@PathVariable String type){
        return new ResponseUtil(200,"Ok",carService.findByType(type));
    }

    //find cars by fuel type
    @GetMapping(path = "/searchByFuelType/{fuelType}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findByFuelType(@PathVariable String fuelType){
        return new ResponseUtil(200,"Ok",carService.findByFuelType(fuelType));
    }





}
