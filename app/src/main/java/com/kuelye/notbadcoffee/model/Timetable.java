package com.kuelye.notbadcoffee.model;

/*
 * Not Bad Coffee for Android. 
 * Copyright (C) 2016 Alexey Leshchuk.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kuelye.notbadcoffee.R;

import java.util.ArrayList;

import static com.kuelye.notbadcoffee.logic.helpers.CalendarHelper.getCalendar;
import static com.kuelye.notbadcoffee.model.TimeRange.MIDNIGHT_TIME_FROM;
import static com.kuelye.notbadcoffee.model.TimeRange.MIDNIGHT_TIME_TO;
import static java.lang.String.format;

public class Timetable extends ArrayList<TimetableRow> {

  // TODO 12h (am/pm) format support
  // TODO closed until
  @NonNull public String getOpenUntilDisplayString(@NonNull Context context) {
    final TimeRange todayTimeRange = getTodayTimeRangeIfOpened();
    if (todayTimeRange == null) {
      return context.getString(R.string.cafe_closed);
    } else {
      final String openUntilTemplate = context.getString(R.string.cafe_open_until_template);
      String timeTo = todayTimeRange.getTimeToAsString();
      if (timeTo.equals(MIDNIGHT_TIME_TO)) {
        timeTo = context.getString(R.string.cafe_time_midnight);
      }

      return format(openUntilTemplate, timeTo);
    }
  }

  public boolean isOpened() {
    return getTodayTimeRangeIfOpened() != null;
  }

  @Nullable TimeRange getTodayTimeRangeIfOpened() {
    final int day = getCalendar().getDay();
    final int hour = getCalendar().getHour();
    final int minute = getCalendar().getMinute();
    for (TimetableRow timetableRow : this) {
      final DayRange dayRange = timetableRow.getDayRange();
      final TimeRange timeRange = timetableRow.getTimeRange();

      if (dayRange.isInclude(day) && timeRange.isInclude(hour, minute)) {
        return timeRange;
      }

      int yesterday = day - 1;
      if (yesterday <= 0) {
        yesterday += 7;
      }
      if (dayRange.isInclude(yesterday) && timeRange.getTimeFrom() > timeRange.getTimeTo()) {
        final TimeRange actualTimeRange = new TimeRange(MIDNIGHT_TIME_FROM, timeRange.getTimeToAsString());
        if (actualTimeRange.isInclude(hour, minute)) {
          return timeRange;
        }
      }
    }

    return null;
  }

}
