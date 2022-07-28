package lk.ijse.spring.controller;


import com.google.gson.Gson;
import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CarSearchDTO;
import lk.ijse.spring.dto.CarTypeSearchDTO;
import lk.ijse.spring.service.CarService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/car")
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    //add cars
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil saveCar(@RequestParam("file") List<MultipartFile> fileList, @RequestParam("model") String model){
        Gson gson = new Gson();
        CarDTO carDTO = gson.fromJson(model, CarDTO.class);
        carService.saveCar(fileList,carDTO);
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

    //getAll cars
    @PostMapping(path = "/findAllCars",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil findAllAvailableCars(@RequestBody CarSearchDTO carSearchDTO){
        return new ResponseUtil(200,"Ok",carService.findAllAvailableCars(carSearchDTO));
    }

    //search car details by many criterias
    @GetMapping(path = "/searchCarDetails",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchCarDetails(@RequestBody CarTypeSearchDTO carTypeSearchDTO){
        carService.findByNoOfPassengersOrTransmissionTypeOrBrandOrTypeOrFuelType(carTypeSearchDTO);
        return new ResponseUtil(200,"Ok",carTypeSearchDTO);
    }


}
