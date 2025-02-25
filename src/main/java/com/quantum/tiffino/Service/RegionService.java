package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Region;
import com.quantum.tiffino.Exception.RegionNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RegionService {

    Region createRegion(Region region);
    Optional<Region> getRegionById(Long id);
    List<Region> getAllRegions();
    Region updateRegion(Long id, Region region) throws RegionNotFoundException;
    void deleteRegion(Long id) throws RegionNotFoundException;
}
