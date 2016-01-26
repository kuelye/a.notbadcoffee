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

public class TimeRange {

  public static final String SEPARATOR = " - ";

  @NonNull private final String mTimeFrom;
  @NonNull private final String mTimeTo;

  public TimeRange(@NonNull String timeFrom, @NonNull String timeTo) {
    mTimeFrom = timeFrom;
    mTimeTo = timeTo;
  }

  @NonNull public String getDisplayString() {
    return mTimeFrom + SEPARATOR + mTimeTo;
  }

  @NonNull public String getTimeFrom() {
    return mTimeFrom;
  }

  @NonNull public String getTimeTo() {
    return mTimeTo;
  }

  @Override
  public String toString() {
    return "TimeRange{" +
        "mTimeFrom='" + mTimeFrom + '\'' +
        ", mTimeTo='" + mTimeTo + '\'' +
        '}';
  }

}
