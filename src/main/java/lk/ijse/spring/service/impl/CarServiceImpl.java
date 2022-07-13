package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.Car;
import lk.ijse.spring.repo.CarRepo;
import lk.ijse.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private ModelMapper mapper;


    @Override
    public void saveCar(CarDTO carDTO) {
       Car car = mapper.map(carDTO,Car.class);
       carRepo.save(car);
    }

    @Override
    public void deleteCar(Integer carId) {
        if(carRepo.existsById(carId)){
            carRepo.deleteById(carId);
        }else{
            throw new RuntimeException("Please check the Car Id...No such Car...!");
        }

    }

    @Override
    public void updateCar(CarDTO carDTO) {
        if (carRepo.existsById(carDTO.getCarId())){
            carRepo.save(mapper.map(carDTO,Car.class));
        }else{
            throw new RuntimeException("No Such Car To Update..! Please Check the ID..!");
        }
    }

    @Override
    public CarDTO searchCar(Integer carId) {
       if(carRepo.existsById(carId)){
           return mapper.map(carRepo.findById(carId).get(),CarDTO.class);
       }else{
           throw new RuntimeException("No such Car for "+carId+"...!");
       }
    }

    @Override
    public List<CarDTO> getAllCars() {
        return mapper.map(carRepo.findAll(),new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> findAllAvailableCars() {
        return null;
    }

    @Override
    public List<CarDTO> findByNoOfPassengers(String noOfPassengers) {
        List<Car> byNoOfPassengers = carRepo.findByNoOfPassengers(noOfPassengers);
        return mapper.map(byNoOfPassengers,new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> findByTransmissionType(String transmissionType) {
        List<Car> byTransmissionType = carRepo.findByTransmissionType(transmissionType);
        return mapper.map(byTransmissionType,new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> findByBrand(String brand) {
        List<Car> byBrand = carRepo.findByBrand(brand);
        return mapper.map(byBrand,new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> findByType(String type) {
        List<Car> byType = carRepo.findByType(type);
        return mapper.map(byType,new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> findByFuelType(String fuelType) {
        List<Car> byFuelType = carRepo.findByFuelType(fuelType);
        return mapper.map(byFuelType,new TypeToken<List<CarDTO>>() {
        }.getType());
    }


}
