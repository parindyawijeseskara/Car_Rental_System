package lk.ijse.spring.repo;

import lk.ijse.spring.entity.CarRentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRentalRequestRepo extends JpaRepository<CarRentalRequest,Integer> {
    List<CarRentalRequest> findAllByRentalRequestIdRentalRequestId(Integer id);

}
