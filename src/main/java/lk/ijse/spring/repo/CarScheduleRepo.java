package lk.ijse.spring.repo;

import lk.ijse.spring.entity.CarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarScheduleRepo extends JpaRepository<CarSchedule,Integer> {

}
