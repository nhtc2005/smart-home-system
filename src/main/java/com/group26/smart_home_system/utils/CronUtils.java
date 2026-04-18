package com.group26.smart_home_system.utils;

import com.group26.smart_home_system.enums.ScheduleMode;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class CronUtils {

  public static String buildCron(LocalTime time, List<Integer> days, ScheduleMode mode) {

    int hour = time.getHour();
    int minute = time.getMinute();

    // ONCE
    if (mode == ScheduleMode.ONCE) {
      return String.format("0 %d %d * * *", minute, hour);
    }

    // REPEAT
    if (days == null || days.isEmpty()) {
      return String.format("0 %d %d * * *", minute, hour);
    }

    String dayOfWeek = days.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));

    return String.format("0 %d %d ? * %s", minute, hour, dayOfWeek);
  }

}
