package lk.ijse.spring.repo;

import lk.ijse.spring.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RentalRequestRepo extends JpaRepository<RentalRequest,Integer> {
    RentalRequest findByRentalRequestId(Integer rentalRequestId);


}
