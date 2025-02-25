package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Exception.RestaurantNotFoundException;
import com.quantum.tiffino.Service.ForgotPasswordService;
import com.quantum.tiffino.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            String responseMessage = userService.registerdUser(user);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            String responseMessage = userService.loginUserWithEmailAndPassword(user);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        try {
            forgotPasswordService.generateOtpAndSendEmail(email);
            return ResponseEntity.ok("OTP has been sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("otp") String otp,
                                                @RequestParam("newPassword") String newPassword,
                                                @RequestParam("confirmPassword") String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        try {
            Optional<String> otpValidationResult = forgotPasswordService.validateOtpAndResetPassword(otp, newPassword, confirmPassword);
            if (otpValidationResult.isPresent()) {
                return ResponseEntity.ok(otpValidationResult.get());
            } else {
                return ResponseEntity.badRequest().body("Invalid or expired OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error resetting password: " + e.getMessage());
        }
    }

    // New GET Mapping to fetch all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // New GET Mapping to fetch a user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/{id}/restaurants/{restaurantId}")
    public ResponseEntity<String> addRestaurantToUser(@PathVariable Long id, @PathVariable Long restaurantId) {
        userService.addRestaurantToUser(id, restaurantId);
        return ResponseEntity.ok("Restaurant added to user successfully");
    }

    @DeleteMapping("/{id}/restaurants/{restaurantId}")
    public ResponseEntity<String> removeRestaurantFromUser(@PathVariable Long id, @PathVariable Long restaurantId) {
        userService.removeRestaurantFromUser(id, restaurantId);
        return ResponseEntity.ok("Restaurant removed from user successfully");
    }

    @GetMapping("/{id}/restaurants")
    public ResponseEntity<List<Restaurant>> getUserRestaurants(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user.getRestaurants());
    }

}
