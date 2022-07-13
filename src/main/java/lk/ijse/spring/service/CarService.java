package lk.ijse.spring.service;


import lk.ijse.spring.dto.CarDTO;

import java.util.List;

public interface CarService {

    public void saveCar(CarDTO carDTO);

    public void deleteCar(Integer carId);

    public void updateCar(CarDTO carDTO);

    public CarDTO searchCar(Integer carId);

    List<CarDTO>getAllCars();

    List<CarDTO>findAllAvailableCars();

    List<CarDTO>findByNoOfPassengers(String noOfPassengers);

    List<CarDTO>findByTransmissionType(String transmissionType);

    List<CarDTO>findByBrand(String brand);

    List<CarDTO>findByType(String type);

    List<CarDTO>findByFuelType(String fuelType);



}
