package com.group26.smart_home_system.service.impl;

import static com.group26.smart_home_system.specification.DeviceSpecification.hasLocation;
import static com.group26.smart_home_system.specification.DeviceSpecification.hasUser;
import static com.group26.smart_home_system.specification.DeviceSpecification.searchByKeyword;

import com.group26.smart_home_system.dto.device.CreateDeviceRequest;
import com.group26.smart_home_system.dto.device.DeviceDetailedResponse;
import com.group26.smart_home_system.dto.device.DeviceFilterRequest;
import com.group26.smart_home_system.dto.device.DeviceResponse;
import com.group26.smart_home_system.dto.device.UpdateDeviceRequest;
import com.group26.smart_home_system.entity.Device;
import com.group26.smart_home_system.entity.Location;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.mapper.DeviceMapper;
import com.group26.smart_home_system.repository.DeviceRepository;
import com.group26.smart_home_system.repository.LocationRepository;
import com.group26.smart_home_system.repository.UserRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.DeviceService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

  private final CurrentUser currentUser;
  private final DeviceRepository deviceRepository;
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final DeviceMapper deviceMapper;

  @Value("${location.not.found}")
  private String locationNotFound;

  @Value("${user.not.found}")
  private String userNotFound;

  @Value("${device.not.found}")
  private String deviceNotFound;

  @Override
  @Transactional
  public DeviceResponse createDevice(CreateDeviceRequest createDeviceRequest)
      throws UserNotFoundException, LocationNotFoundException {
    User user = userRepository.findById(currentUser.getUserId())
        .orElseThrow(() -> new UserNotFoundException(userNotFound));
    Location location = locationRepository.findById(createDeviceRequest.getLocationId())
        .orElseThrow(() -> new LocationNotFoundException(locationNotFound));
    Device newDevice = deviceMapper.toEntity(createDeviceRequest);
    newDevice.setUser(user);
    newDevice.setLocation(location);
    deviceRepository.save(newDevice);
    return deviceMapper.toResponse(newDevice);
  }

  @Override
  public List<DeviceResponse> getAllDevices() {
    List<Device> devices = deviceRepository.findByUserId(currentUser.getUserId());
    return deviceMapper.toResponseList(devices);
  }

  @Override
  public DeviceDetailedResponse getDeviceById(Long deviceId) throws DeviceNotFoundException {
    Device device = deviceRepository.findDetailedByIdAndUserId(deviceId, currentUser.getUserId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));
    return deviceMapper.toDetailedResponse(device);
  }

  @Override
  public Page<DeviceResponse> searchDevices(DeviceFilterRequest deviceFilterRequest,
      Pageable pageable) {
    Specification<Device> spec = Specification
        .where(hasUser(currentUser.getUserId()))
        .and(hasLocation(deviceFilterRequest.getLocationId()))
        .and(searchByKeyword(deviceFilterRequest.getKeyword()));

    return deviceRepository.findAll(spec, pageable)
        .map(deviceMapper::toResponse);
  }

  @Override
  @Transactional
  public DeviceResponse updateDevice(Long deviceId, UpdateDeviceRequest updateDeviceRequest)
      throws DeviceNotFoundException, LocationNotFoundException {
    Device device = deviceRepository.findByIdAndUserId(deviceId, currentUser.getUserId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));
    Location location = locationRepository.findById(updateDeviceRequest.getLocationId())
        .orElseThrow(() -> new LocationNotFoundException(locationNotFound));
    device.setName(updateDeviceRequest.getName());
    device.setLocation(location);
    return deviceMapper.toResponse(deviceRepository.save(device));
  }

  @Override
  @Transactional
  public void deleteDevice(Long deviceId) throws DeviceNotFoundException {
    Device device = deviceRepository.findByIdAndUserId(deviceId, currentUser.getUserId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));
    deviceRepository.delete(device);
  }

}
