package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Payment;
import com.quantum.tiffino.Entity.PaymentStatus;

import java.util.Date;

public interface PaymentService {
    Payment createPayment(double amount, String paymentMethod, Date paymentDate, PaymentStatus paymentStatus);
    Payment getPaymentById(Long paymentId);
}
