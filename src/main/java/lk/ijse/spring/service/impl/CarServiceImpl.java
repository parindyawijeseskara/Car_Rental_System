package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CarSearchDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.Car;
import lk.ijse.spring.entity.CarRentalRequest;
import lk.ijse.spring.entity.RentalRequest;
import lk.ijse.spring.repo.CarRentalRequestRepo;
import lk.ijse.spring.repo.CarRepo;
import lk.ijse.spring.repo.RentalRequestRepo;
import lk.ijse.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private RentalRequestRepo rentalRequestRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CarRentalRequestRepo carRentalRequestRepo;


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
    public List<CarDTO> findAllAvailableCars(CarSearchDTO carSearchDTO) {
        /** get available list */
        List<RentalRequest> list = rentalRequestRepo.findAllByPickUpDateBetweenOrReturnDateBetween(carSearchDTO.getFromDate(),
                carSearchDTO.getToDate(),carSearchDTO.getFromDate(),carSearchDTO.getToDate());
        System.out.println(list.size());

        List<Car> cardIdList = new ArrayList<>();
        List<CarDTO> availableCars = new ArrayList<>();

        List<Car> all1 = carRepo.findAll();

        for (RentalRequest rentalRequest:list) {
            Integer requestId = rentalRequest.getRentalRequestId();
            List<CarRentalRequest> all = carRentalRequestRepo.findAllByRentalRequestIdRentalRequestId(requestId);

            if (!all.isEmpty() && all.size() >0){
                for (CarRentalRequest carRentalRequest:all) {
                    Car car = carRentalRequest.getCarId();

                    if (!cardIdList.contains(car)){
                        cardIdList.add(car);
                    }
                }
            }
        }

        for (Car car:cardIdList) {
            if (all1.contains(car)){
                all1.remove(car);
            }
        }

        for (Car car:all1) {
            CarDTO carDTO = new CarDTO();
            carDTO.setCarId(car.getCarId());
            carDTO.setBrand(car.getBrand());
            // set other fileds

            availableCars.add(carDTO);
        }

        return availableCars;
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
