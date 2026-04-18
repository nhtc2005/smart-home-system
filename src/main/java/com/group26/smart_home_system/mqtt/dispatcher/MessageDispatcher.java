package com.group26.smart_home_system.integration.mqtt.dispatcher;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.integration.mqtt.handler.MessageHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDispatcher {

  private final List<MessageHandler> handlers;

  @Value("${mqtt.unsupported.topic}")
  private String unsupportedTopic;

  public void dispatch(ParsedFeed feed, String payload) {
    for (MessageHandler handler : handlers) {
      System.out.println("Checking handler: " + handler.getClass().getSimpleName());

      if (handler.canHandle(feed)) {
        handler.handle(feed, payload);
        return;
      }
    }

    throw new RuntimeException(String.format(unsupportedTopic, feed.getType()));
  }

}
