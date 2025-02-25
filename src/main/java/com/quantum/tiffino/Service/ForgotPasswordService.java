package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.ForgotPasswordOtp;

import java.util.Optional;

public interface ForgotPasswordService {

    void generateOtpAndSendEmail(String email)throws Exception;

    Optional<String> validateOtpAndResetPassword(String otp, String newPassword,String confirmPassword);
}
