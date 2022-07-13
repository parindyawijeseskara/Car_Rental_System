package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.service.DriverService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    ModelMapper mapper;


    @Override
    public void saveDriver(DriverDTO driverDTO) {
        Driver driver = mapper.map(driverDTO, Driver.class);
        driverRepo.save(driver);
    }

    @Override
    public void deleteDriver(Integer driverId) {
        if(driverRepo.existsById(driverId)){
            driverRepo.deleteById(driverId);
        }else{
        throw new RuntimeException("Please check the Driver Id...No such Driver...!");
        }
    }

    @Override
    public void updateDriver(DriverDTO driverDTO) {
        if(driverRepo.existsById(driverDTO.getDriverId())){
            driverRepo.save(mapper.map(driverDTO,Driver.class));
        }else{
            throw new RuntimeException("No Such Driver To Update..! Please Check the ID..!");
        }
    }

    @Override
    public DriverDTO searchDriver(Integer driverId) {
        if(driverRepo.existsById(driverId)){
            return mapper.map(driverRepo.findById(driverId).get(),DriverDTO.class);
        }else{
            throw new RuntimeException("No such Driver for "+driverId+"...!");
        }
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return mapper.map(driverRepo.findAll(),new TypeToken<List<DriverDTO>>() {
        }.getType());
    }
}
