package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.ResponseMessage;
import com.quantum.tiffino.Service.DeliveryPersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/delivery-persons")
public class DeliveryPersonController {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryPersonController.class);


    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonController(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("aadharNumber") String aadharNumber,
            @RequestParam("panNumber") String panNumber,
            @RequestParam("licenseNumber") String licenseNumber,
            @RequestParam("bankAccountNumber") String bankAccountNumber,
            @RequestParam("ifscCode") String ifscCode,
            @RequestParam("profilePicture") MultipartFile profilePicture,
            HttpServletRequest request) throws IOException {

        // Log the full request parameters to check what is missing
        logger.info("Received request parameters: {}", request.getParameterMap());

        return deliveryPersonService.register(
                name, phoneNumber, vehicleType, email, password,
                aadharNumber, panNumber, licenseNumber, bankAccountNumber, ifscCode,
                profilePicture
        );
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam("selfieFile") MultipartFile selfieFile) throws IOException {

        return deliveryPersonService.login(email, password, selfieFile);
    }


    @PostMapping("/verify/{id}")
    public ResponseEntity<ResponseMessage> verify(@PathVariable Long id, @RequestParam boolean approve) {
        return deliveryPersonService.verifyDeliveryPerson(id, approve);
    }

    @PutMapping("/{id}/available/{status}")
    public ResponseEntity<ResponseMessage> updateAvailability(@PathVariable Long id, @PathVariable boolean status) {
        ResponseMessage response = new ResponseMessage("Availability updated successfully", true);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping
    public ResponseEntity<ResponseMessage> getAll() {
        return deliveryPersonService.getAllDeliveryPersons();
    }
}
