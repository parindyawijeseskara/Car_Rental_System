package lk.ijse.spring.service;


import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CarSearchDTO;
import lk.ijse.spring.dto.CarTypeSearchDTO;

import java.util.List;

public interface CarService {

    public void saveCar(CarDTO carDTO);

    public void deleteCar(Integer carId);

    public void updateCar(CarDTO carDTO);

    public CarDTO searchCar(Integer carId);

    List<CarDTO>getAllCars();

    List<CarDTO>findAllAvailableCars(CarSearchDTO carSearchDTO);

    List<CarDTO>findByNoOfPassengers(String noOfPassengers);

    List<CarDTO>findByTransmissionType(String transmissionType);

    List<CarDTO>findByBrand(String brand);

    List<CarDTO>findByType(String type);

    List<CarDTO>findByFuelType(String fuelType);

    List<CarDTO> findByNoOfPassengersOrTransmissionTypeOrBrandOrTypeOrFuelType(CarTypeSearchDTO carTypeSearchDTO);


}
