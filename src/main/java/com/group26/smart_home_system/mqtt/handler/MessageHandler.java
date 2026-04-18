package com.group26.smart_home_system.mqtt.handler;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;

public interface MessageHandler {

  boolean canHandle(ParsedFeed parsedFeed);

  void handle(ParsedFeed parsedFeed, String payload);

}
