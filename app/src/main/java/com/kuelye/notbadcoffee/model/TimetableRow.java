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

public class TimetableRow {

  @NonNull private final DayRange mDayRange;
  @NonNull private final TimeRange mTimeRange;

  public TimetableRow(@NonNull DayRange dayRange, @NonNull TimeRange timeRange) {
    mDayRange = dayRange;
    mTimeRange = timeRange;
  }

  @NonNull public DayRange getDayRange() {
    return mDayRange;
  }

  @NonNull public TimeRange getTimeRange() {
    return mTimeRange;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TimetableRow that = (TimetableRow) o;

    if (!mDayRange.equals(that.mDayRange)) return false;
    return mTimeRange.equals(that.mTimeRange);
  }

  @Override
  public String toString() {
    return "TimetableRow{" +
        "mDayRange=" + mDayRange +
        ", mTimeRange=" + mTimeRange +
        '}';
  }

}
