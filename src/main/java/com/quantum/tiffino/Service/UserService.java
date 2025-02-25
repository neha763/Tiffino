package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(String username, String email, String password, List<String> roles,String phoneNumber);


    Optional<User> findByUsername(String username);


    List<User> getAllUsers();


    void registerAdmin(String username, String email, String password);

    UserDetails loadUserByUsername(String username);

   Optional<User> getUserById(Long id);

    String registerdUser(User user);
    String loginUserWithEmailAndPassword(User user);


    void addRestaurantToUser(Long id, Long restaurantId);

    void removeRestaurantFromUser(Long id, Long restaurantId);

    List<Restaurant> getRestaurantsById(Long id);
}
