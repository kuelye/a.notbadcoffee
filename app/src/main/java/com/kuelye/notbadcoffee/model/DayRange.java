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

import com.kuelye.notbadcoffee.R;

public class DayRange {

  public static final int SUNDAY     = 0x1000000;
  public static final int MONDAY     = 0x0100000;
  public static final int TUESDAY    = 0x0010000;
  public static final int WEDNESDAY  = 0x0001000;
  public static final int THURSDAY   = 0x0000100;
  public static final int FRIDAY     = 0x0000010;
  public static final int SATURDAY   = 0x0000001;
  public static final int WEEKDAYS   = 0x0111110;
  public static final int WEEKEND    = 0x1000001;

  private final int mMask;

  public DayRange(int mask) {
    mMask = mask;
  }

  @NonNull public String getDisplayString(@NonNull Context context) {
    switch (mMask) {
      case WEEKDAYS:
        return context.getString(R.string.cafe_row_timetable_weekdays);
      case WEEKEND:
        return context.getString(R.string.cafe_row_timetable_weekend);
      // TODO 0x0100000, 0x0110100, ...
      default:
        return "";
    }
  }

  @Override
  public String toString() {
    return "DayRange{" +
        "mMask=" + mMask +
        '}';
  }

}
