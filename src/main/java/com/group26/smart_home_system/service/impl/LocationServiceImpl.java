package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.location.CreateLocationRequest;
import com.group26.smart_home_system.dto.location.LocationDetailedResponse;
import com.group26.smart_home_system.dto.location.LocationResponse;
import com.group26.smart_home_system.dto.location.UpdateLocationRequest;
import com.group26.smart_home_system.entity.Location;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.mapper.LocationMapper;
import com.group26.smart_home_system.repository.LocationRepository;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.LocationService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final CurrentUser currentUser;
  private final LocationRepository locationRepository;
  private final UserRepository userRepository;
  private final LocationMapper locationMapper;

  @Value("${location.not.found}")
  private String locationNotFound;

  @Value("${user.not.found}")
  private String userNotFound;

  @Override
  @Transactional
  public LocationResponse createLocation(CreateLocationRequest createLocationRequest)
      throws UserNotFoundException {
    Location newLocation = locationMapper.toEntity(createLocationRequest);
    User user = userRepository.findById(currentUser.getUserId())
        .orElseThrow(() -> new UserNotFoundException(userNotFound));
    newLocation.setUser(user);
    locationRepository.save(newLocation);
    return locationMapper.toResponse(newLocation);
  }

  @Override
  public List<LocationResponse> getAllLocations() {
    List<Location> locations = locationRepository.findByUserId(currentUser.getUserId());
    return locationMapper.toResponseList(locations);
  }

  @Override
  public LocationDetailedResponse getLocationById(Long locationId)
      throws LocationNotFoundException {
    Location location = locationRepository.findByIdAndUserId(locationId, currentUser.getUserId())
        .orElseThrow(() -> new LocationNotFoundException(locationNotFound));
    return locationMapper.toDetailedResponse(location);
  }

  @Override
  @Transactional
  public LocationResponse updateLocation(Long locationId,
      UpdateLocationRequest updateLocationRequest)
      throws LocationNotFoundException {
    Location location = locationRepository.findByIdAndUserId(locationId, currentUser.getUserId())
        .orElseThrow(() -> new LocationNotFoundException(locationNotFound));
    location.setName(updateLocationRequest.getName());
    locationRepository.save(location);
    return locationMapper.toResponse(location);
  }

  @Override
  @Transactional
  public void deleteLocation(Long locationId) throws LocationNotFoundException {
    Location location = locationRepository.findByIdAndUserId(locationId, currentUser.getUserId())
        .orElseThrow(() -> new LocationNotFoundException(locationNotFound));
    locationRepository.delete(location);
  }

}
