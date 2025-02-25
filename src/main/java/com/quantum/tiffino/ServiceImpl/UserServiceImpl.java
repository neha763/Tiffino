package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Entity.Role;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.RestaurantRepository;
import com.quantum.tiffino.Repository.RoleRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RestaurantRepository restaurantRepository;


    @PostConstruct
    public void init() {

        if (!roleRepository.existsByName("SUPERADMIN")) {

            Role superAdminRole = new Role();
            superAdminRole.setName("SUPERADMIN");
            roleRepository.save(superAdminRole);
            System.out.println("SUPERADMIN role created.");
        }


        Optional<User> superAdmin = userRepository.findByUsername("superadmin");
        if (!superAdmin.isPresent()) {

            Role superAdminRole = roleRepository.findByName("SUPERADMIN")
                    .orElseThrow(() -> new RuntimeException("SUPERADMIN role not found"));

            User superAdminUser = new User("superadmin", passwordEncoder.encode("superadmin123"), "superadmin@example.com", List.of(superAdminRole));


            userRepository.save(superAdminUser);
            System.out.println("SUPERADMIN user created with username 'superadmin'");
        }
    }

    @Override
    public User registerUser(String username, String email, String password, List<String> roles,String phoneNumber) {

        String encodedPassword = passwordEncoder.encode(password);
        List<Role> userRoles = new ArrayList<>();
        for (String roleName : roles) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " is not valid."));
            userRoles.add(role);
        }

        User newUser = new User(username, encodedPassword, email, userRoles);
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void registerAdmin(String username, String email, String password) {

        String encodedPassword = passwordEncoder.encode(password);


        Role superAdminRole = roleRepository.findByName("SUPERADMIN")
                .orElseThrow(() -> new RuntimeException("SUPERADMIN role not found"));


        User superAdmin = new User(username, encodedPassword, email, List.of(superAdminRole));


        userRepository.save(superAdmin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));


        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));  // Add "ROLE_" prefix
        }


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public String registerdUser(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            user.setRoles(null);
            userRepository.save(user);


            return "User registered successfully!";
        } catch (Exception e) {

            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public String loginUserWithEmailAndPassword(User user) {
        try {

            Optional<User> foundUser = userRepository.findByPhoneNumber(user.getPhoneNumber());

            if (foundUser.isPresent()) {

                return "Login successful for user";
            } else {

                throw new RuntimeException("User not found with provided email");
            }
        } catch (Exception e) {

            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    @Override
    public void addRestaurantToUser(Long id, Long restaurantId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        user.getRestaurants().add(restaurant);
        userRepository.save(user);
    }

    @Override
    public void removeRestaurantFromUser(Long id, Long restaurantId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        user.getRestaurants().remove(restaurant);
        userRepository.save(user);
    }

    @Override
    public List<Restaurant> getRestaurantsById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRestaurants();
    }

}
