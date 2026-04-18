package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.location.CreateLocationRequest;
import com.group26.smart_home_system.dto.location.LocationDetailedResponse;
import com.group26.smart_home_system.dto.location.LocationResponse;
import com.group26.smart_home_system.dto.location.UpdateLocationRequest;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<LocationResponse> createLocation(
      @RequestBody CreateLocationRequest createLocationRequest) throws UserNotFoundException {
    LocationResponse locationResponse = locationService.createLocation(createLocationRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(locationResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/all")
  public ResponseEntity<List<LocationResponse>> getAllLocations() {
    return ResponseEntity.ok(locationService.getAllLocations());
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{locationId}")
  public ResponseEntity<LocationDetailedResponse> getLocation(
      @PathVariable("locationId") Long locationId)
      throws LocationNotFoundException {
    return ResponseEntity.ok(locationService.getLocationById(locationId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{locationId}")
  public ResponseEntity<LocationResponse> updateLocation(
      @PathVariable("locationId") Long locationId,
      @RequestBody UpdateLocationRequest updateLocationRequest) throws LocationNotFoundException {
    LocationResponse locationResponse = locationService.updateLocation(locationId,
        updateLocationRequest);
    return ResponseEntity.ok(locationResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{locationId}")
  public ResponseEntity<?> deleteLocation(@PathVariable("locationId") Long locationId)
      throws LocationNotFoundException {
    locationService.deleteLocation(locationId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
