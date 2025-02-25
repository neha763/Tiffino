package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Location;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(User user);
    Location findByUserAndLatitudeAndLongitude(User user, Double latitude,Double longitude);
}
