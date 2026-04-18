package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.location.CreateLocationRequest;
import com.group26.smart_home_system.dto.location.LocationDetailedResponse;
import com.group26.smart_home_system.dto.location.LocationResponse;
import com.group26.smart_home_system.dto.location.UpdateLocationRequest;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import java.util.List;

public interface LocationService {

  LocationResponse createLocation(CreateLocationRequest createLocationRequest)
      throws UserNotFoundException;

  List<LocationResponse> getAllLocations();

  LocationDetailedResponse getLocationById(Long locationId) throws LocationNotFoundException;

  LocationResponse updateLocation(Long locationId, UpdateLocationRequest updateLocationRequest)
      throws LocationNotFoundException;

  void deleteLocation(Long locationId) throws LocationNotFoundException;

}
