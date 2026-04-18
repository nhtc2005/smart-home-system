package com.group26.smart_home_system.integration.mqtt.handler;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;

public interface MessageHandler {

  boolean canHandle(ParsedFeed feed);

  void handle(ParsedFeed feed, String payload);

}
