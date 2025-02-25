package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Region;

import com.quantum.tiffino.Exception.RegionNotFoundException;
import com.quantum.tiffino.Repository.RegionRepository;
import com.quantum.tiffino.Service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    // Create a new region
    @Override
    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }

    // Get a region by ID
    @Override
    public Optional<Region> getRegionById(Long id) {
        return regionRepository.findById(id);
    }

    // Get all regions
    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    // Update a region
    @Override
    public Region updateRegion(Long id, Region region) throws RegionNotFoundException {
        if (regionRepository.existsById(id)) {
            region.setId(id);
            return regionRepository.save(region);
        } else {
            throw new RegionNotFoundException("Region with ID " + id + " not found");
        }
    }

    // Delete a region by ID
    @Override
    public void deleteRegion(Long id) throws RegionNotFoundException {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        } else {
            throw new RegionNotFoundException("Region with ID " + id + " not found");
        }
    }
}
