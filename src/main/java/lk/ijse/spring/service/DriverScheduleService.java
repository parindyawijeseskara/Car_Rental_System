package lk.ijse.spring.service;

import lk.ijse.spring.dto.DriverScheduleDTO;

import java.util.List;

public interface DriverScheduleService {

    public void saveDriverSchedule(DriverScheduleDTO driverScheduleDTO);

    public void deleteDriverSchedule(Integer driverScheduleId);

    public void updateDriverSchedule(DriverScheduleDTO driverScheduleDTO);

    public DriverScheduleDTO searchDriverSchedule(Integer driverScheduleId);

    List<DriverScheduleDTO> getAllDriverSchedule();

}
