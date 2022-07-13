package lk.ijse.spring.repo;

import lk.ijse.spring.entity.CarRentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRequestRepo extends JpaRepository<CarRentalRequest,Integer> {



}
