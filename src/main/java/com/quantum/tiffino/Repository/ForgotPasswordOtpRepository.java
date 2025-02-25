package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.ForgotPasswordOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordOtpRepository extends JpaRepository<ForgotPasswordOtp, Long> {
    ForgotPasswordOtp findByOtp(String otp);
}
