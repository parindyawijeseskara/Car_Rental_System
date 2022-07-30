package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CarSearchDTO;
import lk.ijse.spring.dto.CarTypeSearchDTO;
import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.*;
import lk.ijse.spring.repo.*;
import lk.ijse.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private RentalRequestRepo rentalRequestRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CarRentalRequestRepo carRentalRequestRepo;


    @Override
    public void saveCar(List<MultipartFile>fileList, CarDTO carDTO) {
//        Car save = carRepo.save(mapper.map(carDTO, Car.class));

        User userId = userRepo.findByUserId(carDTO.getCreatedBy());
        Car car = new Car();
        car.setCarId(carDTO.getCarId());
        car.setRegisterNo(carDTO.getRegisterNo());
        car.setBrand(carDTO.getBrand());
        car.setType(carDTO.getType());
        car.setColour(carDTO.getColour());
        car.setMaintainence(carDTO.getMaintainence());
        car.setCreatedOn(carDTO.getCreatedOn());
        car.setTransmissionType(carDTO.getTransmissionType());

        car.setStatus(carDTO.getStatus());
        car.setMonthlyRate(carDTO.getMonthlyRate());
        car.setDailyRate(carDTO.getDailyRate());
        car.setFreeKmPerDay(carDTO.getFreeKmPerDay());
        car.setFreeKmPerMonth(carDTO.getFreeKmPerMonth());
        car.setPricePerExtraKm(carDTO.getPricePerExtraKm());
        car.setNoOfPassengers(carDTO.getNoOfPassengers());
        car.setFuelType(carDTO.getFuelType());
        car.setDamageOrNot(carDTO.getDamageOrNot());
        car.setCreatedBy(userId);



        carRepo.save(car);
        Car carId = carRepo.findByCarId(car.getCarId());

        //document save
        if (!fileList.isEmpty()) {
            fileList.forEach((doc) -> {
                Document document = new Document();

                try {

                    int typeIndex = doc.getOriginalFilename().indexOf('/');
                    int extensionIndex = doc.getOriginalFilename().lastIndexOf('.');
                    String fileName = doc.getOriginalFilename().substring(typeIndex + 1, extensionIndex);

                    String  fileNameWithExtension = doc.getOriginalFilename().substring(typeIndex + 1);

                    document.setName(fileName);
                    document.setContent(doc.getBytes());
                    document.setUploadedBy(car.getCreatedBy());
                    document.setUploadedOn(new Date());
                    document.setPaymentId(null);
                    document.setCarId(carId);
                    document.setDocumentTypeId(null);

                    documentRepo.save(document);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

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
        List<Car> carList = carRepo.findAll();
        List<CarDTO> carDTOS = new ArrayList<>();

        for (Car car:carList) {
            CarDTO carDTO = new CarDTO();
            carDTO.setBrand(car.getBrand());
            carDTO.setCarId(car.getCarId());
            carDTO.setColour(car.getColour());
//            carDTO.setCreatedBy(car.getCreatedBy().getUserId());
            carDTO.setType(car.getType());
            carDTO.setTransmissionType(car.getTransmissionType());
            carDTO.setRegisterNo(car.getRegisterNo());
            carDTO.setNoOfPassengers(car.getNoOfPassengers());
            carDTO.setMonthlyRate(car.getMonthlyRate());
            carDTO.setMaintainence(car.getMaintainence());
            carDTO.setFuelType(car.getFuelType());
//            carDTO.setFreeMileage(car.getFreeMileage());
//            carDTO.setExtraKm(car.getExtraKm());
//            carDTO.setDailyRate(car.getDailyRate());
//            carDTO.setExtraKm(car.getExtraKm());
            carDTO.setDailyRate(car.getDailyRate());
            carDTO.setFreeKmPerDay(car.getFreeKmPerDay());
            carDTO.setFreeKmPerMonth(car.getFreeKmPerMonth());
            carDTO.setPricePerExtraKm(car.getPricePerExtraKm());

            carDTOS.add(carDTO);
        }

        return  carDTOS;

    }

    @Override
    public List<CarDTO> findAllAvailableCars(CarSearchDTO carSearchDTO) {
        /** get availbale list */
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

        System.out.println("List size"+cardIdList.size());
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

    @Override
    public List<CarDTO> findByNoOfPassengersOrTransmissionTypeOrBrandOrTypeOrFuelType(CarTypeSearchDTO carTypeSearchDTO) {
        List<Car> byFuelType = carRepo.findByNoOfPassengersOrTransmissionTypeOrBrandOrTypeOrFuelType(carTypeSearchDTO.getNoOfPessangers(),
                carTypeSearchDTO.getTransmission(),carTypeSearchDTO.getBrand(),carTypeSearchDTO.getType(),carTypeSearchDTO.getFuelType());
        return mapper.map(byFuelType,new TypeToken<List<CarDTO>>() {
        }.getType());
    }


}
