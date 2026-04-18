package com.group26.smart_home_system.service.impl;

import static com.group26.smart_home_system.specification.ActuatorSpecification.hasDevice;
import static com.group26.smart_home_system.specification.ActuatorSpecification.hasType;
import static com.group26.smart_home_system.specification.ActuatorSpecification.hasUser;
import static com.group26.smart_home_system.specification.ActuatorSpecification.searchByKeyword;

import com.group26.smart_home_system.dto.actuator.ActuatorFilterRequest;
import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.actuator.CreateActuatorRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorModeRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorStateRequest;
import com.group26.smart_home_system.dto.actuator.UpdateActuatorRequest;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.CommandLog;
import com.group26.smart_home_system.entity.Device;
import com.group26.smart_home_system.enums.ActuatorMode;
import com.group26.smart_home_system.enums.CommandSource;
import com.group26.smart_home_system.enums.CommandStatus;
import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.mapper.ActuatorMapper;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.DeviceRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.ActuatorService;
import com.group26.smart_home_system.service.CommandLogService;
import com.group26.smart_home_system.service.DynamicScheduleService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActuatorServiceImpl implements ActuatorService {

  private final CurrentUser currentUser;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final CommandLogService commandLogService;
  private final DynamicScheduleService dynamicScheduleService;
  private final ActuatorRepository actuatorRepository;
  private final DeviceRepository deviceRepository;
  private final ActuatorMapper actuatorMapper;

  @Value("${device.not.found}")
  private String deviceNotFound;

  @Value("${actuator.not.found}")
  private String actuatorNotFound;

  @Override
  @Transactional
  public ActuatorResponse createActuator(CreateActuatorRequest request)
      throws DeviceNotFoundException {

    Actuator actuator = actuatorMapper.toEntity(request);

    Device device = deviceRepository.findById(request.getDeviceId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));

    actuator.setDevice(device);

    actuatorRepository.save(actuator);
    actuator.setMqttTopic("device-" + device.getId() + "-actuator-" + actuator.getId());

    return actuatorMapper.toResponse(actuator);
  }

  @Override
  public List<ActuatorResponse> getAllActuators() {
    List<Actuator> actuators = actuatorRepository.findByUserId(currentUser.getUserId());
    return actuatorMapper.toResponseList(actuators);
  }

  @Override
  public ActuatorResponse getActuatorById(Long actuatorId) throws ActuatorNotFoundException {
    Actuator actuator = actuatorRepository
        .findByIdAndUserId(actuatorId, currentUser.getUserId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));

    return actuatorMapper.toResponse(actuator);
  }

  @Override
  public Page<ActuatorResponse> searchActuators(ActuatorFilterRequest actuatorFilterRequest,
      Pageable pageable) {
    Specification<Actuator> spec = Specification
        .where(hasUser(currentUser.getUserId()))
        .and(hasDevice(actuatorFilterRequest.getDeviceId()))
        .and(hasType(actuatorFilterRequest.getType()))
        .and(searchByKeyword(actuatorFilterRequest.getKeyword()));

    return actuatorRepository.findAll(spec, pageable)
        .map(actuatorMapper::toResponse);
  }

  @Override
  @Transactional
  public ActuatorResponse updateActuator(Long actuatorId, UpdateActuatorRequest request)
      throws ActuatorNotFoundException, DeviceNotFoundException {

    Actuator actuator = actuatorRepository
        .findByIdAndUserId(actuatorId, currentUser.getUserId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));

    Device device = deviceRepository
        .findByIdAndUserId(request.getDeviceId(), currentUser.getUserId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));

    actuator.setName(request.getName());
    actuator.setDevice(device);

    return actuatorMapper.toResponse(actuatorRepository.save(actuator));
  }

  @Override
  @Transactional
  public ActuatorResponse setActuatorState(Long actuatorId,
      SetActuatorStateRequest setActuatorStateRequest) throws ActuatorNotFoundException {
    Actuator actuator = actuatorRepository
        .findByIdAndUserId(actuatorId, currentUser.getUserId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));

    actuator.setState(setActuatorStateRequest.getState());

    ActuatorMessageEvent actuatorMessageEvent = new ActuatorMessageEvent(
        actuator.getDevice().getId(),
        actuatorId,
        setActuatorStateRequest.getState().name(),
        Instant.now());
    applicationEventPublisher.publishEvent(actuatorMessageEvent);

    CommandLog commandLog = new CommandLog();
    commandLog.setActuator(actuator);
    commandLog.setCommand(setActuatorStateRequest.getState().name());
    commandLog.setSource(CommandSource.USER);
    commandLog.setStatus(CommandStatus.SENT);
    commandLogService.save(commandLog);

    return actuatorMapper.toResponse(actuatorRepository.save(actuator));
  }

  @Override
  @Transactional
  public ActuatorResponse setActuatorState(ActuatorStateEvent actuatorStateEvent) {
    Actuator actuator = actuatorRepository.findById(actuatorStateEvent.getActuatorId())
        .orElseThrow();

    actuator.setState(actuatorStateEvent.getActuatorState());
    return actuatorMapper.toResponse(actuatorRepository.save(actuator));
  }

  @Override
  @Transactional
  public ActuatorResponse setActuatorMode(Long actuatorId,
      SetActuatorModeRequest setActuatorModeRequest) throws ActuatorNotFoundException {
    Actuator actuator = actuatorRepository
        .findByIdAndUserId(actuatorId, currentUser.getUserId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));

    actuator.setMode(setActuatorModeRequest.getMode());

    if (setActuatorModeRequest.getMode() == ActuatorMode.MANUAL) {
      dynamicScheduleService.cancelByActuator(actuatorId);
    } else if (setActuatorModeRequest.getMode() == ActuatorMode.SCHEDULE) {
      dynamicScheduleService.rescheduleByActuator(actuatorId);
    }

    return actuatorMapper.toResponse(actuatorRepository.save(actuator));
  }

  @Override
  @Transactional
  public void deleteActuator(Long actuatorId) throws ActuatorNotFoundException {
    Actuator actuator = actuatorRepository
        .findByIdAndUserId(actuatorId, currentUser.getUserId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));
    actuatorRepository.delete(actuator);
  }

}
