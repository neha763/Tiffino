package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.ForgotPasswordOtp;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.ForgotPasswordOtpRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordOtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void generateOtpAndSendEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found with the provided email"));


        String otp = generateOtp();


        ForgotPasswordOtp forgotPasswordOtp = new ForgotPasswordOtp();
        forgotPasswordOtp.setOtp(otp);
        forgotPasswordOtp.setCreatedAt(LocalDateTime.now());
        forgotPasswordOtp.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        forgotPasswordOtp.setUser(user);


        otpRepository.save(forgotPasswordOtp);


        sendOtpEmail(user.getEmail(), otp);
    }


    @Override
    public Optional<String> validateOtpAndResetPassword(String otp, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return Optional.of("Passwords do not match.");
        }


        ForgotPasswordOtp otpRecord = otpRepository.findByOtp(otp);
        if (otpRecord == null || otpRecord.getExpiryDate().isBefore(LocalDateTime.now())) {
            return Optional.empty();
        }


        User user = otpRecord.getUser();


        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);


        userRepository.save(user);



        return Optional.of("Password reset successfully.");
    }


    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000); // Generate 6-digit OTP
        return String.valueOf(otp);
    }


    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);

        mailSender.send(message);
    }
}
