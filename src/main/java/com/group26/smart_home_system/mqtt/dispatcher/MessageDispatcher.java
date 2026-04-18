package com.group26.smart_home_system.mqtt.dispatcher;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.mqtt.handler.MessageHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageDispatcher {

  private final List<MessageHandler> handlers;

  public void dispatch(ParsedFeed feed, String payload) {
    for (MessageHandler handler : handlers) {
      if (handler.canHandle(feed)) {
        log.debug("MQTT message handled by {}", handler.getClass().getSimpleName());

        handler.handle(feed, payload);
        return;
      }
    }

    log.warn("No handler found for feed: {}", feed);
  }

}
