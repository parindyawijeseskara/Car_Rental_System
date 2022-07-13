package lk.ijse.spring.service;

import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.entity.Driver;

import java.util.List;

public interface DriverService {

    public void saveDriver(DriverDTO driverDTO);

    public void deleteDriver(Integer driverId);

    public void updateDriver(DriverDTO driverDTO);

    public DriverDTO searchDriver(Integer driverId);

    List<DriverDTO> getAllDrivers();


}
