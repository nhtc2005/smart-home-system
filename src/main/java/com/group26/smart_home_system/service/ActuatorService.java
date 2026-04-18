package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.actuator.ActuatorFilterRequest;
import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.actuator.CreateActuatorRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorModeRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorStateRequest;
import com.group26.smart_home_system.dto.actuator.UpdateActuatorRequest;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActuatorService {

  ActuatorResponse createActuator(CreateActuatorRequest createActuatorRequest)
      throws DeviceNotFoundException;

  List<ActuatorResponse> getAllActuators();

  ActuatorResponse getActuatorById(Long actuatorId) throws ActuatorNotFoundException;

  Page<ActuatorResponse> searchActuators(ActuatorFilterRequest actuatorFilterRequest,
      Pageable pageable);

  ActuatorResponse updateActuator(Long actuatorId, UpdateActuatorRequest updateActuatorRequest)
      throws ActuatorNotFoundException, DeviceNotFoundException;

  ActuatorResponse setActuatorState(Long actuatorId,
      SetActuatorStateRequest setActuatorStateRequest) throws ActuatorNotFoundException;

  ActuatorResponse setActuatorState(ActuatorStateEvent actuatorStateEvent);

  ActuatorResponse setActuatorMode(Long actuatorId, SetActuatorModeRequest setActuatorModeRequest)
      throws ActuatorNotFoundException;

  void deleteActuator(Long actuatorId) throws ActuatorNotFoundException;

}
