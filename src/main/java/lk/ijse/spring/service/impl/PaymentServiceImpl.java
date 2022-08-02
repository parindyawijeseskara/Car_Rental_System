package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.PaymentDTO;
import lk.ijse.spring.dto.RentalRequestDTO;
import lk.ijse.spring.entity.Payment;
import lk.ijse.spring.entity.RentalRequest;
import lk.ijse.spring.entity.User;
import lk.ijse.spring.repo.PaymentRepo;
import lk.ijse.spring.repo.RentalRequestRepo;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RentalRequestRepo rentalRequestRepo;

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepo.findAll();
        List<PaymentDTO> paymentDTO = new ArrayList<>();

        for (Payment payment:payments) {
            PaymentDTO paymentDTO1 = new PaymentDTO();
            paymentDTO1.setPaymentId(payment.getPaymentId());
            paymentDTO1.setDate(payment.getDate());
            paymentDTO1.setAmount(payment.getRent_amount());
            paymentDTO1.setTotalAmount(payment.getTotal_amount());

            User userId = userRepo.findByUserId(payment.getPaymentDoneBy().getUserId());
            paymentDTO1.setUserId(userId.getUserId());
            paymentDTO1.setRentalRequestId(payment.getRentalRequestId().getRentalRequestId());
            User byUserName = userRepo.findByUserName(payment.getPaymentDoneBy().getUserName());
            paymentDTO1.setUserName(byUserName.getUserName());


            paymentDTO.add(paymentDTO1);
        }
        return paymentDTO;
    }
}












