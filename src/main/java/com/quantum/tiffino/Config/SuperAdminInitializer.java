package com.quantum.tiffino.Config;
import com.quantum.tiffino.Entity.Role;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.RoleRepository;
import com.quantum.tiffino.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import java.util.List;


@Service
public class SuperAdminInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SuperAdminInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initSuperAdmin() {
        String username = "superadmin";
        String password = "superadmin123";

        try {

            if (!userRepository.findByUsername(username).isPresent()) {

                // Create the SUPERADMIN role
                Role superAdminRole = roleRepository.findByName("SUPERADMIN")
                        .orElseThrow(() -> new RuntimeException("SUPERADMIN role not found"));

                User superAdmin = new User(
                        username,
                        passwordEncoder.encode(password),
                        "",
                        List.of(superAdminRole)
                );

                // Save the SuperAdmin user
                userRepository.save(superAdmin);
                logger.info("SuperAdmin user created successfully.");
            } else {
                logger.info("SuperAdmin already exists.");
            }
        } catch (RuntimeException e) {
            logger.error("Error initializing SuperAdmin: " + e.getMessage(), e);
        }
    }
}

