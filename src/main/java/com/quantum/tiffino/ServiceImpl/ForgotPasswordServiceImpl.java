package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Admin;
import com.quantum.tiffino.Entity.ForgotPasswordOtp;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.AdminRepository;
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
    private AdminRepository adminRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Generate OTP and send email for both Users and Admins
     */
    @Override
    public void generateOtpAndSendEmail(String email) throws Exception {
        Object account = findUserOrAdminByEmail(email);
        if (account == null) {
            throw new Exception("User/Admin not found with the provided email");
        }

        // Generate OTP
        String otp = generateOtp();

        // Save OTP in the database
        ForgotPasswordOtp forgotPasswordOtp = new ForgotPasswordOtp();
        forgotPasswordOtp.setOtp(otp);
        forgotPasswordOtp.setCreatedAt(LocalDateTime.now());
        forgotPasswordOtp.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        if (account instanceof User) {
            forgotPasswordOtp.setUser((User) account);
        } else {
            forgotPasswordOtp.setAdmin((Admin) account);
        }

        otpRepository.save(forgotPasswordOtp);

        // Send OTP via email
        sendOtpEmail(email, otp);
    }

    /**
     * Validate OTP and Reset Password for both Users and Admins
     */
    @Override
    public Optional<String> validateOtpAndResetPassword(String otp, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return Optional.of("Passwords do not match.");
        }

        ForgotPasswordOtp otpRecord = otpRepository.findByOtp(otp);
        if (otpRecord == null || otpRecord.getExpiryDate().isBefore(LocalDateTime.now())) {
            return Optional.empty();
        }

        Object account = (otpRecord.getUser() != null) ? otpRecord.getUser() : otpRecord.getAdmin();

        if (account instanceof User) {
            User user = (User) account;
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else if (account instanceof Admin) {
            Admin admin = (Admin) account;
            admin.setPassword(passwordEncoder.encode(newPassword));
            adminRepository.save(admin);
        } else {
            return Optional.empty();
        }

        return Optional.of("Password reset successfully.");
    }

    /**
     * Utility function to find a user or admin by email
     */
    private Object findUserOrAdminByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }

        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.orElse(null);
    }

    /**
     * Generate 6-digit OTP
     */
    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Send OTP email
     */
    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }
}
