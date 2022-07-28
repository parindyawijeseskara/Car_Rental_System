package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CarPaymentDTO;
import lk.ijse.spring.dto.CarTypeDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.entity.*;
import lk.ijse.spring.repo.*;
import lk.ijse.spring.service.RentalRequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.util.resources.cldr.am.CurrencyNames_am;

import java.io.IOException;
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

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    DocumentRepo documentRepo;

    public double payment;
    public double rentalAmount;

    @Override
    public void saveRentalRequest(List<MultipartFile> file,RentalRequestDTO rentalRequestDTO) {

        /** Get Data Difference and Save Payment Info */
        long diff = rentalRequestDTO.getReturnDate().getTime() - rentalRequestDTO.getPickUpDate().getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        System.out.println("No of days :"+diffDays);

        /**Save data in rental request table when putting a request*/
        for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
            Car car = carRepo.findByCarId(carPaymentDTO.getCarId());
            this.rentalAmount += car.getDailyRate()*diffDays;
        }
        rentalRequestDTO.setRentalFee(this.rentalAmount);
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setRentalFee(this.rentalAmount);
        rentalRequest.setDamageOrNot(rentalRequestDTO.getDamageOrNot());
        rentalRequest.setComment(rentalRequestDTO.getComment());
        rentalRequest.setReturnDate(rentalRequestDTO.getReturnDate());
        rentalRequest.setStatus("PEN");
        rentalRequest.setCreatedBy(userRepo.findByNic(rentalRequestDTO.getNic()));
        rentalRequest.setPickUpDate(rentalRequestDTO.getPickUpDate());
        rentalRequest.setPickuptime(rentalRequestDTO.getPickuptime());
        rentalRequest.setReturntime(rentalRequestDTO.getReturntime());
        RentalRequest save = requestRepo.save(rentalRequest);
        Integer rentalRequestID = save.getRentalRequestId();

        /** Save Document Files */
        if (!file.isEmpty()) {
            file.forEach((doc) -> {
                Document document = new Document();

                try {

                    int typeIndex = doc.getOriginalFilename().indexOf('/');
                    int extensionIndex = doc.getOriginalFilename().lastIndexOf('.');
                    String fileName = doc.getOriginalFilename().substring(typeIndex + 1, extensionIndex);

                    String  fileNameWithExtension = doc.getOriginalFilename().substring(typeIndex + 1);

                    document.setName(fileName);
                    document.setContent(doc.getBytes());
                    document.setUploadedBy(null);
                    document.setUploadedOn(new Date());
                    document.setPaymentId(null);
                    document.setCarId(null);
                    document.setDocumentTypeId(null);
                    document.setRentalRequestId(save);

                    documentRepo.save(document);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        /** Update Car Details when putting a request*/
        for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
            Car car = carRepo.findByCarId(carPaymentDTO.getCarId());
            car.setStatus("Reserved");
            car.setCarId(carPaymentDTO.getCarId());

            User userId = userRepo.findByNic(rentalRequestDTO.getNic());
            car.setUpdatedBy(userId);
            car.setUpdatedOn(new Date());
            carRepo.save(car);
        }


        if (rentalRequestDTO.getDriverId() != null){
            /** Update driver details when putting a request*/
            for (Integer driverId:rentalRequestDTO.getDriverId()) {
                Driver driver = driverRepo.findByDriverId(driverId);
                driver.setStatus("Reserved");

                User userId = userRepo.findByNic(rentalRequestDTO.getNic());
                driver.setUpdatedBy(userId);
                driver.setStatus("UnAvailable");
                driver.setUpdatedOn(new Date());
                driverRepo.save(driver);
            }
        }

        /**Save data in carRentalRequest table when putting a request*/
        for (CarPaymentDTO carPaymentDTO :rentalRequestDTO.getCarPayment()) {
            /** Get Car Entity By Car Id */
            Car car = carRepo.findByCarId(carPaymentDTO.getCarId());

            RentalRequest rentalRequestId = requestRepo.findByRentalRequestId(rentalRequestID);

            CarRentalRequest carRentalRequest = new CarRentalRequest();
            carRentalRequest.setCarRentalRequestId(1);
            carRentalRequest.setCarId(car);
            carRentalRequest.setRentalRequestId(rentalRequestId);
            carRentalRequest.setLossDamagePayment(carPaymentDTO.getLossDamagePayment());

            carRentalRequestRepo.save(carRentalRequest);
        }

        if (rentalRequestDTO.getDriverId() != null){
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
        }

        /**Save data to carSchedule table when putting a request*/
        for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
            Car carId1 = carRepo.findByCarId(carPaymentDTO.getCarId());

            CarSchedule carSchedule = new CarSchedule();
            carSchedule.setCarId(carId1);
            carSchedule.setStartDate(rentalRequestDTO.getPickUpDate());
            carSchedule.setEndDate(rentalRequestDTO.getReturnDate());
            carSchedule.setCarScheduleId(1);
            carScheduleRepo.save(carSchedule);
        }

        double lossDamagePayment = 0.0;
        for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
            /** Save Loss Damage Amount */
            lossDamagePayment += carPaymentDTO.getLossDamagePayment();
        }
        /** Save Loss Damage Amount */
        Payment payment = new Payment();
        payment.setDate(new Date());
        payment.setLoss_damage_payment(lossDamagePayment);
        payment.setPaymentDoneBy(userRepo.findByNic(rentalRequestDTO.getNic()));
        payment.setRentalRequestId(save);
        Payment payment1 = paymentRepo.save(payment);


        Payment byPaymentId = paymentRepo.findByPaymentId(payment1.getPaymentId());
        byPaymentId.setRent_amount(this.rentalAmount);
        byPaymentId.setTotal_amount(this.rentalAmount+byPaymentId.getLoss_damage_payment());
        paymentRepo.save(byPaymentId);

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
            for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
                Car car = carRepo.findByCarId(carPaymentDTO.getCarId());
                car.setStatus("Reserved");
                car.setCarId(carPaymentDTO.getCarId());

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
            for (CarPaymentDTO carPaymentDTO :rentalRequestDTO.getCarPayment()) {
                /** Get Car Entity By Car Id */
                Car car = carRepo.findByCarId(carPaymentDTO.getCarId());

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
            for (CarPaymentDTO carPaymentDTO:rentalRequestDTO.getCarPayment()) {
                Car carId1 = carRepo.findByCarId(carPaymentDTO.getCarId());

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
            rentalRequestDTO.setPickUpDate(byRentalRequestId.getPickUpDate());
            rentalRequestDTO.setStatus(byRentalRequestId.getStatus());
            rentalRequestDTO.setReturnDate(byRentalRequestId.getReturnDate());
            rentalRequestDTO.setRentalFee(byRentalRequestId.getRentalFee());
            rentalRequestDTO.setRentalRequestId(rentalRequestId);

            List<CarPaymentDTO> carIdList = new ArrayList<>();
            List<String> carNameList = new ArrayList<>();
            List<CarRentalRequest> all = carRentalRequestRepo.findAllByRentalRequestIdRentalRequestId(rentalRequestId);
            for (CarRentalRequest carRentalRequest:all) {
                Integer carId = carRentalRequest.getCarId().getCarId();
                double lossDamage = carRentalRequest.getLossDamagePayment();

                CarPaymentDTO carPaymentDTO = new CarPaymentDTO();
                carPaymentDTO.setCarId(carId);
                carPaymentDTO.setLossDamagePayment(lossDamage);

                carIdList.add(carPaymentDTO);

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

            rentalRequestDTO.setCarPayment(carIdList);
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

    @Override
    public List<RentalRequestDTO> getAllPendingRequests() {
        return mapper.map(requestRepo.getAllByStatus("PEN"),new TypeToken<List<RentalRequestDTO>>() {
        }.getType());
    }

    @Override
    public double getLossDamageAmount(CarTypeDTO carTypeDTOS) {
        double lossDamagePayment = 0.0;
        for (Integer carId:carTypeDTOS.getCarId()) {

            System.out.println(carId);
            Car car = carRepo.findByCarId(carId);
            System.out.println(car.getType());
            if (car.getType().equals("General")) {
                lossDamagePayment = 10000.00;
            }

            if (car.getType().equals("Premium")) {
                lossDamagePayment = 15000.00;
            }

            if (car.getType().equals("Luxury")){
                lossDamagePayment = 20000.00;
            }
        }
        return lossDamagePayment;
    }

    @Override
    public double getRentalFeeToPay(CarTypeDTO carTypeDTOS) {
        /** Get Data Difference and Save Payment Info */
        long diff = carTypeDTOS.getReturnDate().getTime() - carTypeDTOS.getPickUpDate().getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        System.out.println("No of days :"+diffDays);
        /**Save data in rental request table when putting a request*/
        for (Integer carId:carTypeDTOS.getCarId()) {
            Car car = carRepo.findByCarId(carId);
            this.rentalAmount += car.getDailyRate()*diffDays;
        }

        return this.rentalAmount;
    }
}
