package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.entity.*;
import lk.ijse.spring.repo.*;
import lk.ijse.spring.service.RentalRequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.resources.cldr.am.CurrencyNames_am;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class RentalRequestServiceImpl implements RentalRequestService {

    @Autowired
    RentalRequestRepo requestRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CarRentalRequestRepo carRentalRequestRepo;

    @Autowired
    RentalRequestDriverRepo rentalRequestDriverRepo;

    @Autowired
    DriverScheduleRepo driverScheduleRepo;

    @Autowired
    CarScheduleRepo carScheduleRepo;

    @Override
    public void saveRentalRequest(RentalRequestDTO rentalRequestDTO) {

        /**Save data in rental request table when putting a request*/
        RentalRequest rentalRequest = mapper.map(rentalRequestDTO, RentalRequest.class);
        RentalRequest save = requestRepo.save(rentalRequest);
        Integer rentalRequestID = save.getRentalRequestId();


        /** Update Car Details when putting a request*/
        for (Integer carId:rentalRequestDTO.getCarId()) {
            Car car = carRepo.findByCarId(carId);
            car.setStatus("Reserved");
            car.setCarId(carId);

            User userId = userRepo.findByUserId(rentalRequestDTO.getUpdatedBy());
            car.setUpdatedBy(userId);
            car.setUpdatedOn(new Date());
            carRepo.save(car);
        }

        /** Update driver details when putting a request*/
        for (Integer driverId:rentalRequestDTO.getDriverId()) {
            Driver driver = driverRepo.findByDriverId(driverId);
            driver.setStatus("Reserved");

            User userId = userRepo.findByUserId(rentalRequestDTO.getUpdatedBy());
            driver.setUpdatedBy(userId);
            driver.setStatus("UnAvailable");
            driver.setUpdatedOn(new Date());
            driverRepo.save(driver);
        }

        /**Save data in carRentalRequest table when putting a request*/
        for (Integer carId :rentalRequestDTO.getCarId()) {
            /** Get Car Entity By Car Id */
            Car car = carRepo.findByCarId(carId);

            RentalRequest rentalRequestId = requestRepo.findByRentalRequestId(rentalRequestID);

            CarRentalRequest carRentalRequest = new CarRentalRequest();
            carRentalRequest.setCarRentalRequestId(1);
            carRentalRequest.setCarId(car);
            carRentalRequest.setRentalRequestId(rentalRequestId);

            carRentalRequestRepo.save(carRentalRequest);
        }

        /**save data to rentalRequestDriver table when putting a request*/

        for (Integer driverId:rentalRequestDTO.getDriverId()) {
            Driver byDriverId = driverRepo.findByDriverId(driverId);
            RentalRequest rentalRequestId = requestRepo.findByRentalRequestId(rentalRequestID);

            RentalRequestDriver rentalRequestDriver = new RentalRequestDriver();
            rentalRequestDriver.setDriverId(byDriverId);
            rentalRequestDriver.setRentalRequestId(rentalRequestId);
            rentalRequestDriver.setRentalRequestDriverId(1);
            rentalRequestDriverRepo.save(rentalRequestDriver);

        }

        /**Save data to driverSchedule table when putting a rental request*/
        for (Integer driverId:rentalRequestDTO.getDriverId()) {
            Driver driverId1 = driverRepo.findByDriverId(driverId);

            DriverSchedule driverSchedule = new DriverSchedule();
            driverSchedule.setDriverId(driverId1);
            driverSchedule.setStartDate(rentalRequestDTO.getPickUpDate());
            driverSchedule.setEndDate(rentalRequestDTO.getReturnDate());
            driverSchedule.setDriverScheduleId(1);
            driverScheduleRepo.save(driverSchedule);

        }

        /**Save data to carSchedule table when putting a request*/
        for (Integer carId:rentalRequestDTO.getCarId()) {
            Car carId1 = carRepo.findByCarId(carId);

            CarSchedule carSchedule = new CarSchedule();
            carSchedule.setCarId(carId1);
            carSchedule.setStartDate(rentalRequestDTO.getPickUpDate());
            carSchedule.setEndDate(rentalRequestDTO.getReturnDate());
            carSchedule.setCarScheduleId(1);
            carScheduleRepo.save(carSchedule);
        }

    }

    @Override
    public void deleteRentalRequest(Integer rentalRequestId) {
        if (requestRepo.existsById(rentalRequestId)){
            requestRepo.deleteById(rentalRequestId);
        }else{
            throw new RuntimeException("Please check the Rental Request Id...No such Rental Request...!");
        }
    }

    @Override
    public void updateRentalRequest(RentalRequestDTO rentalRequestDTO) {
        if (requestRepo.existsById(rentalRequestDTO.getRentalRequestId())){
            requestRepo.save(mapper.map(rentalRequestDTO,RentalRequest.class));

            RentalRequestDTO rentalRequestDTO1 = this.searchRentalRequest(rentalRequestDTO.getRentalRequestId());
            RentalRequest rentalRequest = mapper.map(rentalRequestDTO1, RentalRequest.class);

            rentalRequest.setPickUpDate(rentalRequestDTO.getPickUpDate());
            rentalRequest.setReturnDate(rentalRequestDTO.getReturnDate());
            rentalRequest.setComment(rentalRequestDTO.getComment());
            rentalRequest.setDamageOrNot(rentalRequestDTO.getDamageOrNot());
            rentalRequest.setUpdatedOn(new Date());
            rentalRequest.setRentalFee(rentalRequestDTO.getRentalFee());
            rentalRequest.setRentalRequestId(rentalRequest.getRentalRequestId());

            requestRepo.save(rentalRequest);

            /** Update Car Details when putting a request*/
            for (Integer carId:rentalRequestDTO.getCarId()) {
                Car car = carRepo.findByCarId(carId);
                car.setStatus("Reserved");
                car.setCarId(carId);

                User userId = userRepo.findByUserId(rentalRequestDTO.getUpdatedBy());
                car.setUpdatedBy(userId);
                car.setUpdatedOn(new Date());
                carRepo.save(car);
            }


            /** Update driver details when putting a request*/
            for (Integer driverId:rentalRequestDTO.getDriverId()) {
                Driver driver = driverRepo.findByDriverId(driverId);
                driver.setStatus("Reserved");

                User userId = userRepo.findByUserId(rentalRequestDTO.getUpdatedBy());
                driver.setUpdatedBy(userId);
                driver.setStatus("UnAvailable");
                driver.setUpdatedOn(new Date());
                driverRepo.save(driver);
            }

            /**Save data in carRentalRequest table when putting a request*/
            for (Integer carId :rentalRequestDTO.getCarId()) {
                /** Get Car Entity By Car Id */
                Car car = carRepo.findByCarId(carId);

                RentalRequest rentalRequestId = requestRepo.findByRentalRequestId(rentalRequestDTO.getRentalRequestId());

                CarRentalRequest carRentalRequest = new CarRentalRequest();
                carRentalRequest.setCarRentalRequestId(1);
                carRentalRequest.setCarId(car);
                carRentalRequest.setRentalRequestId(rentalRequestId);

                carRentalRequestRepo.save(carRentalRequest);
            }

            /**save data to rentalRequestDriver table when putting a request*/

            for (Integer driverId:rentalRequestDTO.getDriverId()) {
                Driver byDriverId = driverRepo.findByDriverId(driverId);
                RentalRequest rentalRequestId = requestRepo.findByRentalRequestId(rentalRequestDTO.getRentalRequestId());

                RentalRequestDriver rentalRequestDriver = new RentalRequestDriver();
                rentalRequestDriver.setDriverId(byDriverId);
                rentalRequestDriver.setRentalRequestId(rentalRequestId);
                rentalRequestDriver.setRentalRequestDriverId(1);
                rentalRequestDriverRepo.save(rentalRequestDriver);

            }

            /**Save data to driverSchedule table when putting a rental request*/
            for (Integer driverId:rentalRequestDTO.getDriverId()) {
                Driver driverId1 = driverRepo.findByDriverId(driverId);

                DriverSchedule driverSchedule = new DriverSchedule();
                driverSchedule.setDriverId(driverId1);
                driverSchedule.setStartDate(rentalRequestDTO.getPickUpDate());
                driverSchedule.setEndDate(rentalRequestDTO.getReturnDate());
                driverSchedule.setDriverScheduleId(1);
                driverScheduleRepo.save(driverSchedule);

            }

            /**Save data to carSchedule table when putting a request*/
            for (Integer carId:rentalRequestDTO.getCarId()) {
                Car carId1 = carRepo.findByCarId(carId);

                CarSchedule carSchedule = new CarSchedule();
                carSchedule.setCarId(carId1);
                carSchedule.setStartDate(rentalRequestDTO.getPickUpDate());
                carSchedule.setEndDate(rentalRequestDTO.getReturnDate());
                carSchedule.setCarScheduleId(1);
                carScheduleRepo.save(carSchedule);
            }

        }else{
            throw new RuntimeException("No Such Rental Request To Update..! Please Check the ID..!");
        }
    }

    @Override
    public RentalRequestDTO searchRentalRequest(Integer rentalRequestId) {
        if (requestRepo.existsById(rentalRequestId)){
            /** Search By Rental RequestId and set data */
            RentalRequest byRentalRequestId = requestRepo.findByRentalRequestId(rentalRequestId);

            RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
            rentalRequestDTO.setDamageOrNot(byRentalRequestId.getDamageOrNot());
            rentalRequestDTO.setComment(byRentalRequestId.getComment());
            rentalRequestDTO.setLossDamagePayment(byRentalRequestId.getLossDamagePayment());
            rentalRequestDTO.setPickUpDate(byRentalRequestId.getPickUpDate());
            rentalRequestDTO.setStatus(byRentalRequestId.getStatus());
            rentalRequestDTO.setReturnDate(byRentalRequestId.getReturnDate());
            rentalRequestDTO.setRentalFee(byRentalRequestId.getRentalFee());
            rentalRequestDTO.setRentalRequestId(rentalRequestId);

            List<Integer> carIdList = new ArrayList<>();
            List<String> carNameList = new ArrayList<>();
            List<CarRentalRequest> all = carRentalRequestRepo.findAllByRentalRequestIdRentalRequestId(rentalRequestId);
            for (CarRentalRequest carRentalRequest:all) {
                Integer carId = carRentalRequest.getCarId().getCarId();
                carIdList.add(carId);

                String carName = carRentalRequest.getCarId().getType();
                carNameList.add(carName);
            }


            List<Integer> driverIdList = new ArrayList<>();
            List<String> driverNameList = new ArrayList<>();
            List<RentalRequestDriver> driverList = rentalRequestDriverRepo.findAllByRentalRequestIdRentalRequestId(rentalRequestId);
            for (RentalRequestDriver rentalRequestDriver:driverList) {
                Integer driverId = rentalRequestDriver.getDriverId().getDriverId();
                driverIdList.add(driverId);

                String driverName = rentalRequestDriver.getDriverId().getName();
                driverNameList.add(driverName);
            }

            rentalRequestDTO.setCarId(carIdList);
            rentalRequestDTO.setCarNameList(carNameList);
            rentalRequestDTO.setDriverId(driverIdList);
            rentalRequestDTO.setDriverNameList(driverNameList);

            return rentalRequestDTO;

        }else{
            throw new RuntimeException("No such Rental Request for "+rentalRequestId+"...!");
        }
    }

    @Override
    public List<RentalRequestDTO> getAllRequests() {
        return mapper.map(requestRepo.findAll(),new TypeToken<List<RentalRequestDTO>>() {
        }.getType());
    }
}
