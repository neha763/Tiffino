package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Admin;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AdminService {

    // String registerAdmin(Admin admin);
    String loginAdmin(String username, String password);

    @Transactional
    Admin updateAdmin(Long adminId, Admin adminDetails);

    void deleteAdmin(Long adminId);

    Admin getAdminById(Long adminId);

    // void registerAdmin(String username, String email, String password);

    String registerAdmin(@Valid Admin admin, String role);

    //void updateUserPassword(User user);
    void updateProfilePicture(Long adminId, byte[] profilePicture);

    void generateOtpAndSendEmail(String email) throws Exception;

    Optional<String> validateOtpAndResetPassword(String otp, String newPassword, String confirmPassword);

    void credentialSendToEmail(String email);

    @Transactional
    String replaceMainManager(Long adminId, Admin newManager);
}