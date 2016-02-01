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

import android.support.annotation.NonNull;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class TimeRange {

  public static final String MIDNIGHT_TIME_TO = "24:00";
  public static final String MIDNIGHT_TIME_FROM = "00:00";
  public static final String TIME_RANGE_SEPARATOR = " - ";
  public static final String TIME_RANGE_TEMPLATE = "%s" + TIME_RANGE_SEPARATOR + "%s";
  public static final String TIME_SEPARATOR = ":";
  public static final String TIME_TEMPLATE = "%02d" + TIME_SEPARATOR + "%02d";
  public static final int MINUTES_IN_HOUR = 60;

  private final int mTimeFrom;
  private final int mTimeTo;

  public TimeRange(@NonNull String timeFrom, @NonNull String timeTo) {
    mTimeFrom = toMinutes(timeFrom);
    mTimeTo = toMinutes(timeTo);
  }

  @NonNull public String getDisplayString() {
    return format(TIME_RANGE_TEMPLATE, toString(mTimeFrom), toString(mTimeTo));
  }

  public int getTimeFrom() {
    return mTimeFrom;
  }

  public String getTimeFromAsString() {
    return toString(mTimeFrom);
  }

  public int getTimeTo() {
    return mTimeTo;
  }

  public String getTimeToAsString() {
    return toString(mTimeTo);
  }

  public boolean isIncludeTime(int hour, int minutes) {
    return isIncludeTime(hour * MINUTES_IN_HOUR + minutes);
  }
  
  public boolean isIncludeTime(int time) {
    if (mTimeTo == mTimeFrom) {
      return true;
    } else if (mTimeTo < mTimeFrom) {
      return (time < mTimeTo || time >= mTimeFrom);
    } else {
      return (time >= mTimeFrom && time < mTimeTo);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TimeRange timeRange = (TimeRange) o;

    if (mTimeFrom != timeRange.mTimeFrom) return false;
    return mTimeTo == timeRange.mTimeTo;
  }

  @Override
  public String toString() {
    return "TimeRange{" +
        "mTimeFrom=" + mTimeFrom +
        ", mTimeTo=" + mTimeTo +
        '}';
  }

  /* ========================== HIDDEN ============================== */

  private int toMinutes(@NonNull String timeAsString) {
    final String[] parts = timeAsString.split(TIME_SEPARATOR);
    return parseInt(parts[0]) * MINUTES_IN_HOUR + parseInt(parts[1]);
  }

  private String toString(int timeMinutes) {
    final int hour = timeMinutes / MINUTES_IN_HOUR;
    final int minute = timeMinutes % MINUTES_IN_HOUR;
    return format(TIME_TEMPLATE, hour, minute);
  }

}
