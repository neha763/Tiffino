package com.quantum.tiffino.Service;
import com.quantum.tiffino.Entity.Location;

import java.util.List;

public interface LocationService {
    Location saveLocations( Location location);
    Location updateLocationById( Long locationId,  Location location);
    String deleteLocationById( Long locationId);
    List<Location> getLocationByUserName( String userName);
    List<Location> getAllLocations();
}
