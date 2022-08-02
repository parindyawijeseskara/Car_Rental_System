package lk.ijse.spring.repo;

import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.entity.Payment;
import lk.ijse.spring.entity.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
    Payment findByPaymentId(Integer id);

    RentalRequestDTO findAllByRentalRequestIdRentalRequestId(Integer rentalRequestId);
}
