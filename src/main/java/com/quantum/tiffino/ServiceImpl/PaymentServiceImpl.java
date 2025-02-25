package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Payment;
import com.quantum.tiffino.Entity.PaymentStatus;
import com.quantum.tiffino.Repository.PaymentRepository;
import com.quantum.tiffino.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment createPayment(double amount, String paymentMethod, Date paymentDate, PaymentStatus paymentStatus) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(paymentDate);
        payment.setPaymentStatus(paymentStatus);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(()->new RuntimeException("payment not found"));

    }
}
