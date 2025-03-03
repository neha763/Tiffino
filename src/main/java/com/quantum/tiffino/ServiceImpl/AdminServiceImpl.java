package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.CommonUtil.ValidationClass;
import com.quantum.tiffino.Entity.Admin;

import com.quantum.tiffino.Entity.ForgotPasswordOtp;
import com.quantum.tiffino.Exception.AdminNotFoundException;
import com.quantum.tiffino.Repository.AdminRepository;
import com.quantum.tiffino.Repository.ForgotPasswordOtpRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ForgotPasswordOtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    @Transactional
    public String registerAdmin(Admin admin, String loggedInUserRole) {
        logger.info("Registering admin with username: {}", admin.getUsername());


        if (!"ROLE_SUPERADMIN".equals(loggedInUserRole)) {
            logger.error("Only superadmin can register new admins.");
            throw new RuntimeException("Only superadmin can register new admins.");
        }


        validateAdminData(admin);


        if (adminRepository.existsByUsername(admin.getUsername()) || adminRepository.existsByEmail(admin.getEmail()) || adminRepository.existsByMobileNo(admin.getMobileNo())) {
            logger.error("Admin with username {} or email {} or mobile number {} already exists", admin.getUsername(), admin.getEmail(), admin.getMobileNo());
            throw new RuntimeException("Admin with this username or email or mobile number already exists");
        }
        String uniqueCode = generateUniqueCode(admin.getCity(), admin.getArea());
        admin.setUniqueCode(uniqueCode);

        // Store the password in plain text initially but require a reset on first login
        admin.setPassword(admin.getPassword());
        admin.setFirstLogin(true);

        // admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        // admin.setPassword(admin.getPassword());


        Admin savedAdmin = adminRepository.save(admin);
        logger.info("Successfully registered admin with ID: {}", savedAdmin.getId());


        return "Admin registered successfully.";
    }

    @Override
    public void updateProfilePicture(Long adminId, byte[] profilePicture) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setProfilePicture(profilePicture);
        adminRepository.save(admin);

    }


    private void validateAdminData(Admin admin) {
        // Validate admin data
        if (admin.getUsername() == null || !ValidationClass.USERNAME_PATTERN.matcher(admin.getUsername()).matches()) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (admin.getEmail() == null || !ValidationClass.EMAIL_PATTERN.matcher(admin.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (admin.getPassword() == null || !ValidationClass.PASSWORD_PATTERN.matcher(admin.getPassword()).matches()) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (admin.getMobileNo() == null || !ValidationClass.PHONE_PATTERN.matcher(admin.getMobileNo()).matches()) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
    }

//    @Override
//    public String loginAdmin(String username, String password) {
//        logger.info("Admin login attempt for username: {}", username);
//
//        Admin admin = adminRepository.findByUsername(username)
//                .orElseThrow(() -> {
//                    logger.error("Admin not found with username: {}", username);
//                    return new RuntimeException("Admin not found");
//                });
//
//
//        if (new BCryptPasswordEncoder().matches(password, admin.getPassword())) {
//            logger.info("Login successful for username: {}", username);
//            return "Login successful!";
//        } else {
//            logger.error("Invalid credentials for username: {}", username);
//            throw new RuntimeException("Invalid credentials");
//        }
//    }

    @Override
    public String loginAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.isFirstLogin()) {
            throw new RuntimeException("Please reset your password before logging in.");
        }

        if (passwordEncoder.matches(password, admin.getPassword())) {
            return "Login successful!";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


//    @Override
//    @Transactional
//    public Admin updateAdmin(Long adminId, Admin adminDetails) {
//        logger.info("Updating admin by id: {}, data: {}", adminId, adminDetails);
//
//
//        Admin existingAdmin = adminRepository.findById(adminId)
//                .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
//
//        // Update fields only if the new value is not null and valid
//        if (adminDetails.getName() != null && ValidationClass.NAME_PATTERN.matcher(adminDetails.getName()).matches()) {
//            existingAdmin.setName(adminDetails.getName());
//        }
//        if (adminDetails.getMobileNo() != null && ValidationClass.PHONE_PATTERN.matcher(adminDetails.getMobileNo()).matches()) {
//            existingAdmin.setMobileNo(adminDetails.getMobileNo());
//        }
//        if (adminDetails.getUsername() != null && ValidationClass.USERNAME_PATTERN.matcher(adminDetails.getUsername()).matches()) {
//            existingAdmin.setUsername(adminDetails.getUsername());
//        }
//        if (adminDetails.getPassword() != null && ValidationClass.PASSWORD_PATTERN.matcher(adminDetails.getPassword()).matches()) {
//            existingAdmin.setPassword(new BCryptPasswordEncoder().encode(adminDetails.getPassword()));  // Encrypt password
//        }
//        if (adminDetails.getEmail() != null && ValidationClass.EMAIL_PATTERN.matcher(adminDetails.getEmail()).matches()) {
//            existingAdmin.setEmail(adminDetails.getEmail());
//        }
//
//
//        Admin updatedAdmin = adminRepository.save(existingAdmin);
//        logger.info("Successfully updated admin with id: {}", adminId);
//        return updatedAdmin;
//    }

    @Override
    @Transactional
    public Admin updateAdmin(Long adminId, Admin adminDetails) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));

        if (adminDetails.getPassword() != null && ValidationClass.PASSWORD_PATTERN.matcher(adminDetails.getPassword()).matches()) {
            existingAdmin.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
            existingAdmin.setFirstLogin(false);  // Reset first login flag after password change
        }

        return adminRepository.save(existingAdmin);
    }


    @Override
    public void deleteAdmin(Long adminId) {
        logger.info("Deleting Admin by id: {}", adminId);
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));

        adminRepository.deleteById(adminId);
        logger.info("Successfully deleted admin with id: {}", adminId);
    }


    @Override
    public Admin getAdminById(Long adminId) {
        logger.info("Fetching admin by ID: {}", adminId);

        return adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
    }

    private String generateUniqueCode(String city, String area) {

        String cityCode = city.toLowerCase(); // Convert city to lowercase for uniformity
        String areaCode = area.toLowerCase().replace(" ", "-"); // Replace spaces with hyphens in area name
        String uniqueSuffix = String.valueOf(System.currentTimeMillis()).substring(6); // Use current time for uniqueness
        return cityCode + "-" + areaCode + "-" + uniqueSuffix;
    }

    @Override
    public void generateOtpAndSendEmail(String email) throws Exception {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found with the provided email"));


        String otp = generateOtp();


        ForgotPasswordOtp forgotPasswordOtp = new ForgotPasswordOtp();
        forgotPasswordOtp.setOtp(otp);
        forgotPasswordOtp.setCreatedAt(LocalDateTime.now());
        forgotPasswordOtp.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        forgotPasswordOtp.setAdmin(admin);


        otpRepository.save(forgotPasswordOtp);


        sendOtpEmail(admin.getEmail(), otp);
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
//        Admin admin = otpRecord.getAdmin();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        admin.setPassword(encodedPassword);
//        adminRepository.save(admin);
//        return Optional.of("Password reset successfully.");

        Admin admin = otpRecord.getAdmin();
        admin.setPassword(passwordEncoder.encode(newPassword));
        admin.setFirstLogin(false);
        adminRepository.save(admin);
        return Optional.of("Password reset successfully.");
    }

    @Override
    public void credentialSendToEmail(String email) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

        if (adminOptional.isEmpty()) {
            throw new RuntimeException("Admin not found with the given email.");
        }

        Admin admin = adminOptional.get();
        String credentialsMessage = "Your Credentials:\nUsername: " + admin.getUsername() + "\nPassword: " + admin.getPassword() + "\nUniqueCode: " + admin.getUniqueCode();

        sendEmail(admin.getEmail(), "Your Admin Credentials", credentialsMessage);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }


    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }


    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);

        mailSender.send(message);


    }


    @Transactional
    @Override
    public String replaceMainManager(Long adminId, Admin newManager) {

        Admin existingManager = adminRepository.findById(adminId)

                .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));


        String uniqueCode = existingManager.getUniqueCode();

        Optional<Admin> usernameConflict = adminRepository.findByUsername(newManager.getUsername());

        if (usernameConflict.isPresent() && !usernameConflict.get().getId().equals(adminId)) {

            throw new RuntimeException("Username already exists. Please choose a different one.");

        }


        existingManager.setUsername(newManager.getUsername());

        existingManager.setPassword(newManager.getPassword());

        existingManager.setEmail(newManager.getEmail());

        existingManager.setName(newManager.getName());

        existingManager.setMobileNo(newManager.getMobileNo());

        existingManager.setFirstLogin(true); // Force the new manager to reset password on first login

        adminRepository.save(existingManager);

        // Send updated credentials via email

        credentialSendToEmail(existingManager.getEmail());

        return "New Main Manager has been assigned successfully.";

    }
}