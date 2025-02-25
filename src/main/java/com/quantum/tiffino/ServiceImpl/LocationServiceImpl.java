package com.quantum.tiffino.ServiceImpl;
import com.quantum.tiffino.Entity.Location;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.LocationRepository;
import com.quantum.tiffino.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location saveLocations(Location location) {
       return locationRepository.save(location);
    }

    @Override
    public Location updateLocationById(Long locationId, Location location) {
        Optional<Location> existingLocation = locationRepository.findById(locationId);

        if (existingLocation.isPresent()) {
            Location updatedLocation = existingLocation.get();
            updatedLocation.setUsername(location.getUsername());
            updatedLocation.setLatitude(location.getLatitude());
            updatedLocation.setLongitude(location.getLongitude());
            updatedLocation.setAddressLine1(location.getAddressLine1());
            updatedLocation.setAddressLine2(location.getAddressLine2());
            updatedLocation.setCity(location.getCity());
            updatedLocation.setState(location.getState());
            updatedLocation.setPostalCode(location.getPostalCode());
            updatedLocation.setUser(location.getUser());


            return locationRepository.save(updatedLocation);

        }
        return null;
    }

    @Override
    public String deleteLocationById(Long locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if(location.isPresent()){
            locationRepository.deleteById(locationId);
            return "Location Deleted Successfully";
        }
        return "Location Not Found!";
    }

    @Override
    public List<Location> getLocationByUserName(String userName) {
        User user = new User();
        user.setUsername(userName);
        return locationRepository.findByUser(user);

    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}

