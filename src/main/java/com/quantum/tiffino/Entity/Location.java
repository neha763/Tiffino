package com.quantum.tiffino.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String fullName;
        private Double latitude;
        private Double longitude;
        private String phoneNumber;
        private String email;

        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnore
//        @JsonBackReference
//        @JsonIgnoreProperties("locations")
        private User user;

        public Location()
        {
        }

        public Location(Long id, String fullName, Double latitude, Double longitude, String phoneNumber, String email, String addressLine1, String addressLine2, String city, String state, String postalCode, User user) {
                this.id = id;
                this.fullName = fullName;
                this.latitude = latitude;
                this.longitude = longitude;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.addressLine1 = addressLine1;
                this.addressLine2 = addressLine2;
                this.city = city;
                this.state = state;
                this.postalCode = postalCode;
                this.user = user;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public Double getLatitude() {
                return latitude;
        }

        public void setLatitude(Double latitude) {
                this.latitude = latitude;
        }

        public Double getLongitude() {
                return longitude;
        }

        public void setLongitude(Double longitude) {
                this.longitude = longitude;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getAddressLine1() {
                return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
                this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
                return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
                this.addressLine2 = addressLine2;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getState() {
                return state;
        }

        public void setState(String state) {
                this.state = state;
        }

        public String getPostalCode() {
                return postalCode;
        }

        public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

}

