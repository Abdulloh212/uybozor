package uz.pdp.uybozor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.entities.Location;
import uz.pdp.uybozor.repo.LocationRepository;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        locationRepository.save(location);
        return ResponseEntity.ok(location);
    }
}
