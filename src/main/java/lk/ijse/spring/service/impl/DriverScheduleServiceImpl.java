package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.DriverScheduleDTO;
import lk.ijse.spring.repo.DriverScheduleRepo;
import lk.ijse.spring.service.DriverScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverScheduleServiceImpl implements DriverScheduleService {

    @Autowired
    DriverScheduleRepo scheduleRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public void saveDriverSchedule(DriverScheduleDTO driverScheduleDTO) {

    }

    @Override
    public void deleteDriverSchedule(Integer driverScheduleId) {

    }

    @Override
    public void updateDriverSchedule(DriverScheduleDTO driverScheduleDTO) {

    }

    @Override
    public DriverScheduleDTO searchDriverSchedule(Integer driverScheduleId) {
        return null;
    }

    @Override
    public List<DriverScheduleDTO> getAllDriverSchedule() {
        return null;
    }
}
