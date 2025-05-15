package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String vehicleType;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus; // PENDING, APPROVED, REJECTED

    private String aadharNumber;
    private String panNumber;
    private String licenseNumber;
    private String bankAccountNumber;
    private String ifscCode;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] selfie;

    private String profilePicture; // Instead of byte[]
    private boolean available = true;

    @ManyToMany(mappedBy = "deliveryPerson")
    @JsonIgnore
    private List<Restaurant> restaurant;

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Delivery> deliveries;

    @Enumerated(EnumType.STRING)
    private DeliveryPersonStatus status = DeliveryPersonStatus.ONLINE;

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    private List<Order> assignedOrders;

    public DeliveryPerson() {
    }

    public DeliveryPerson(Long id, String name, String phoneNumber, String vehicleType, String email, String password, VerificationStatus verificationStatus, String aadharNumber, String panNumber, String licenseNumber, String bankAccountNumber, String ifscCode, byte[] selfie, String profilePicture, boolean available, List<Restaurant> restaurant, List<Delivery> deliveries, DeliveryPersonStatus status, List<Order> assignedOrders) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.vehicleType = vehicleType;
        this.email = email;
        this.password = password;
        this.verificationStatus = verificationStatus;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
        this.licenseNumber = licenseNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.ifscCode = ifscCode;
        this.selfie = selfie;
        this.profilePicture = profilePicture;
        this.available = available;
        this.restaurant = restaurant;
        this.deliveries = deliveries;
        this.status = status;
        this.assignedOrders = assignedOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public byte[] getSelfie() {
        return selfie;
    }

    public void setSelfie(byte[] selfie) {
        this.selfie = selfie;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public DeliveryPersonStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryPersonStatus status) {
        this.status = status;
    }

    public List<Order> getAssignedOrders() {
        return assignedOrders;
    }

    public void setAssignedOrders(List<Order> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    @Override
    public String toString() {
        return "DeliveryPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verificationStatus=" + verificationStatus +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", selfie=" + Arrays.toString(selfie) +
                ", profilePicture='" + profilePicture + '\'' +
                ", available=" + available +
                ", restaurant=" + restaurant +
                ", deliveries=" + deliveries +
                ", status=" + status +
                ", assignedOrders=" + assignedOrders +
                '}';
    }
}