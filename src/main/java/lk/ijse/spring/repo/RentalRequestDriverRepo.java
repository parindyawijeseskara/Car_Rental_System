package lk.ijse.spring.repo;

import lk.ijse.spring.entity.RentalRequestDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRequestDriverRepo extends JpaRepository<RentalRequestDriver,Integer> {
    List<RentalRequestDriver> findAllByRentalRequestIdRentalRequestId(Integer id);

}
