package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Entity.ResponseMessage;
import com.quantum.tiffino.Entity.VerificationStatus;
import com.quantum.tiffino.Exception.ResourceNotFoundException;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Service.DeliveryPersonService;
import com.quantum.tiffino.utils.FaceRecognitionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    private final DeliveryPersonRepository deliveryPersonRepository;
    private static final String PROFILE_PICTURE_DIRECTORY = "uploads/profile_pictures/";
    private static final String SELFIE_DIRECTORY = "uploads/selfies/";

    public DeliveryPersonServiceImpl(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    @Override
    public ResponseEntity<ResponseMessage> register(
            String name, String phoneNumber, String vehicleType, String email, String password,
            String aadharNumber, String panNumber, String licenseNumber, String bankAccountNumber,
            String ifscCode, MultipartFile profilePicture) throws IOException {

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setName(name);
        deliveryPerson.setPhoneNumber(phoneNumber);
        deliveryPerson.setVehicleType(vehicleType);
        deliveryPerson.setEmail(email);
        deliveryPerson.setPassword(password);
        deliveryPerson.setAadharNumber(aadharNumber);
        deliveryPerson.setPanNumber(panNumber);
        deliveryPerson.setLicenseNumber(licenseNumber);
        deliveryPerson.setBankAccountNumber(bankAccountNumber);
        deliveryPerson.setIfscCode(ifscCode);
        deliveryPerson.setVerificationStatus(VerificationStatus.PENDING);

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String profilePicPath = saveFile(PROFILE_PICTURE_DIRECTORY, email, profilePicture);
            deliveryPerson.setProfilePicture(profilePicPath);
        }

        DeliveryPerson savedPerson = deliveryPersonRepository.save(deliveryPerson);
        return ResponseEntity.ok(new ResponseMessage(
                "Registration Successful. Awaiting verification.",
                true, savedPerson));
    }

    @Override
    public ResponseEntity<ResponseMessage> login(String email, String password, MultipartFile selfie) throws IOException {
        DeliveryPerson dp = deliveryPersonRepository.findByEmail(email)
                .filter(person -> person.getPassword().equals(password))
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!dp.getVerificationStatus().equals(VerificationStatus.APPROVED)) {
            return ResponseEntity.badRequest().body(new ResponseMessage(
                    "Your account is not verified yet.", false));
        }

        if (dp.getProfilePicture() == null || dp.getProfilePicture().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage(
                    "Profile picture not found. Please contact admin.", false));
        }

        File profilePicFile = new File(dp.getProfilePicture());
        if (!profilePicFile.exists() || profilePicFile.length() == 0) {
            return ResponseEntity.badRequest().body(new ResponseMessage(
                    "Profile picture file not found at: " + dp.getProfilePicture(), false));
        }

        if (selfie == null || selfie.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage(
                    "Selfie is required for verification.", false));
        }

        // Save selfie to the file system
        String selfiePath = saveFile(SELFIE_DIRECTORY, email, selfie);
        File selfieFile = new File(selfiePath);

        // Perform Face Recognition
        boolean isMatch = FaceRecognitionUtil.compareFaces(profilePicFile, selfieFile);

        if (!isMatch) {
            return ResponseEntity.badRequest().body(new ResponseMessage(
                    "Selfie does not match profile picture. Login failed!", false));
        }

        return ResponseEntity.ok(new ResponseMessage(
                "Login Successful. Selfie verified!", true));
    }

    private String saveFile(String directory, String email, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = email.replaceAll("[.@]", "_") + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        System.out.println("Saving file to: " + filePath.toAbsolutePath());

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(file.getBytes());
        }

        return filePath.toAbsolutePath().toString();
    }

    @Override
    public ResponseEntity<ResponseMessage> verifyDeliveryPerson(Long id, boolean approve) {
        DeliveryPerson dp = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Person not found"));

        dp.setVerificationStatus(approve ? VerificationStatus.APPROVED : VerificationStatus.REJECTED);
        deliveryPersonRepository.save(dp);

        String message = approve ? "Delivery Person approved successfully. Now they can log in."
                : "Delivery Person rejected";

        return ResponseEntity.ok(new ResponseMessage(
                message, true, dp));
    }

    @Override
    public ResponseEntity<ResponseMessage> getAllDeliveryPersons() {
        List<DeliveryPerson> deliveryPersons = deliveryPersonRepository.findAll();
        return ResponseEntity.ok(new ResponseMessage(
                "Delivery Persons fetched successfully", true, deliveryPersons));
    }

    @Override
    public ResponseEntity<ResponseMessage> updateAvailability(Long id, boolean status) {
        DeliveryPerson person = deliveryPersonRepository.findById(id).orElseThrow(() -> new RuntimeException("Delivery Person not found"));
        person.setAvailable(status);
        deliveryPersonRepository.save(person);

        return ResponseEntity.ok(new ResponseMessage("Availability updated successfully", true, person));
    }


}
