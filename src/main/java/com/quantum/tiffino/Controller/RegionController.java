package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Region;
import com.quantum.tiffino.Service.RegionService;
import com.quantum.tiffino.Exception.RegionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
@CrossOrigin("*")
public class RegionController {

    @Autowired
    private RegionService regionService;

    // Create a new region
    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        Region savedRegion = regionService.createRegion(region);
        return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
    }

    // Get a region by its ID
    @GetMapping("/{regionId}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long regionId) {
        Optional<Region> region = regionService.getRegionById(regionId);
        return region.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all regions
    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regions = regionService.getAllRegions();
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    // Update an existing region
    @PutMapping("/{regionId}")
    public ResponseEntity<Region> updateRegion(@PathVariable Long regionId, @RequestBody Region region) {
        try {
            Region updatedRegion = regionService.updateRegion(regionId, region);
            return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
        } catch (RegionNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a region by ID
    @DeleteMapping("/{regionId}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long regionId) {
        try {
            regionService.deleteRegion(regionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
