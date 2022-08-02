package lk.ijse.spring.service;

import lk.ijse.spring.dto.PaymentDTO;
import lk.ijse.spring.dto.RentalRequestDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO>getAllPayments();
}
