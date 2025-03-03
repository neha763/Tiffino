package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.List;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotNull(message = "Mobile number is required")
    private String mobileNo;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;


    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB")
    @Nullable
    private byte[] profilePicture;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String city;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String area;

    @Column(nullable = false, unique = true)
    private String uniqueCode;

    @NotNull(message = "Postal code is required")
    @NotEmpty(message = "Postal code cannot be empty")
    @Size(min = 3, max = 10, message = "Postal code must be between 3 and 10 characters")
    private String postalCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "superadmin_id",referencedColumnName = "id",nullable = false)
    @JsonBackReference
    private Admin superAdmin;

    @OneToMany(mappedBy = "superAdmin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Admin> adminsSupervised;

    @Column(nullable = false)
    private boolean firstLogin = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @NotNull(message = "Role is required")
    private Role role;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Admin getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Admin superAdmin) {
        this.superAdmin = superAdmin;
    }

    public List<Admin> getAdminsSupervised() {
        return adminsSupervised;
    }

    public void setAdminsSupervised(List<Admin> adminsSupervised) {
        this.adminsSupervised = adminsSupervised;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Nullable
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(@Nullable byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public @NotNull @NotEmpty @Size(min = 3, max = 50) String getCity() {
        return city;
    }

    public void setCity(@NotNull @NotEmpty @Size(min = 3, max = 50) String city) {
        this.city = city;
    }

    public @NotNull @NotEmpty @Size(min = 3, max = 50) String getArea() {
        return area;
    }

    public void setArea(@NotNull @NotEmpty @Size(min = 3, max = 50) String area) {
        this.area = area;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
