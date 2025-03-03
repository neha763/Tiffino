package com.quantum.tiffino.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantum.tiffino.Entity.Admin;
import com.quantum.tiffino.Entity.JwtRequest;
import com.quantum.tiffino.Exception.AdminNotFoundException;
import com.quantum.tiffino.Exception.UserNotFoundException;
import com.quantum.tiffino.Repository.AdminRepository;
import com.quantum.tiffino.Repository.RoleRepository;
import com.quantum.tiffino.Security.JwtTokenUtil;
import com.quantum.tiffino.Service.AdminService;
import com.quantum.tiffino.Service.ForgotPasswordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(
            @Valid @RequestPart("adminData") String adminData,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestHeader("Authorization") String authHeader) {

        try {
            // Check Authorization header validity
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.error("Authorization header is missing or invalid");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authorization header is missing or invalid");
            }

            // Extract token and role
            String token = authHeader.substring(7);
            String role = jwtTokenUtil.extractRoleFromToken(token);
            logger.info("Extracted Role: {}", role);

            // Convert received admin data to Admin object
            ObjectMapper objectMapper = new ObjectMapper();
            Admin admin = objectMapper.readValue(adminData, Admin.class);

            // Handle profile picture upload if exists
            if (profilePicture != null) {
                String fileName = profilePicture.getOriginalFilename();
                if (fileName != null && !fileName.matches(".*\\.(jpg|jpeg)$")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile picture must be in JPG or JPEG format.");
                }
                byte[] profilePictureBytes = profilePicture.getBytes();
                admin.setProfilePicture(profilePictureBytes);
            }

            // Register admin
            String message = adminService.registerAdmin(admin, role);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin added successfully!");
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody JwtRequest jwtRequest) {
        try {
            String username = jwtRequest.getUsername();
            String password = jwtRequest.getPassword();

            if (username == null || password == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required.");
            }

            String token = adminService.loginAdmin(username, password);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/update-profile-picture/{adminId}")
    public ResponseEntity<String> updateProfilePicture(
            @PathVariable Long adminId,
            @RequestParam("profilePicture") MultipartFile profilePicture) {

        try {
            // Validate profile picture format
            String fileName = profilePicture.getOriginalFilename();
            if (fileName != null && !fileName.matches(".*\\.(jpg|jpeg)$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile picture must be in JPG or JPEG format.");
            }

            byte[] profilePictureBytes = profilePicture.getBytes();
            adminService.updateProfilePicture(adminId, profilePictureBytes);
            return ResponseEntity.status(HttpStatus.OK).body("Profile picture updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/update/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long adminId, @RequestPart("adminData") String adminData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Admin adminDetails = objectMapper.readValue(adminData, Admin.class);
            Admin updatedAdmin = adminService.updateAdmin(adminId, adminDetails);
            return ResponseEntity.ok(updatedAdmin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse admin data: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Invalid admin data format");
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long adminId) {
        try {
            if (adminId == null) {
                throw new UserNotFoundException("Admin id cannot be null");
            }
            adminService.deleteAdmin(adminId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found with id: " + adminId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getAdminById/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        try {
            Admin admin = adminService.getAdminById(adminId);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (AdminNotFoundException e) {
            logger.error("Admin not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error occurred while fetching admin: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        try {
            logger.info("Forgot password request received for email: {}", email);
            forgotPasswordService.generateOtpAndSendEmail(email);
            return ResponseEntity.ok("OTP has been sent to your email.");
        } catch (Exception e) {
            logger.error("Error during forgot password process: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("otp") String otp,
                                                @RequestParam("newPassword") String newPassword,
                                                @RequestParam("confirmPassword") String confirmPassword) {
        var otpValidationResult = forgotPasswordService.validateOtpAndResetPassword(otp, newPassword,confirmPassword);
        if (otpValidationResult.isPresent()) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }
    }



    @PostMapping("/credential")
    public ResponseEntity<String> saveCredential(@RequestParam("email") String email) {
        try {
            logger.info("Sending credentials to email: {}", email);
            adminService.credentialSendToEmail(email);
            return ResponseEntity.ok("Credentials sent to email successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/replace-manager/{adminId}")
    public ResponseEntity<String> replaceMainManager(@PathVariable Long adminId,
                                                     @RequestBody Admin newManager) {
        try {
            String response = adminService.replaceMainManager(adminId, newManager);
            return ResponseEntity.ok(response);
        }
        catch(AdminNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}