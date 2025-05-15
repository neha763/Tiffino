package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.DeliveryPerson;

import com.quantum.tiffino.Entity.ResponseMessage;
import jakarta.mail.Multipart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DeliveryPersonService {
        ResponseEntity<ResponseMessage> register(String name, String phoneNumber, String vehicleType, String email, String password,
                                                 String aadharNumber, String panNumber, String licenseNumber, String bankAccountNumber,
                                                 String ifscCode, MultipartFile profilePicture) throws IOException;
        ResponseEntity<ResponseMessage> login(String email, String password, MultipartFile selfieFile) throws IOException;
        ResponseEntity<ResponseMessage> verifyDeliveryPerson(Long id, boolean approve);
        ResponseEntity<ResponseMessage> getAllDeliveryPersons();

    ResponseEntity<ResponseMessage> updateAvailability(Long id, boolean status);
}
