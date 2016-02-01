package com.kuelye.notbadcoffee.logic.helpers;

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

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public final class CalendarHelper {

  @NonNull private static Calendar sCalendar = new CalendarDefault();

  public static void setCalendar(@NonNull Calendar calendar) {
    sCalendar = calendar;
  }

  public static Calendar getCalendar() {
    return sCalendar;
  }

  /* =========================== INNER ============================== */

  public interface Calendar {
    int getDay();
    int getHour();
    int getMinute();
  }

  private static class CalendarDefault implements Calendar {

    @Override
    public int getDay() {
      return java.util.Calendar.getInstance().get(DAY_OF_WEEK);
    }

    @Override
    public int getHour() {
      return java.util.Calendar.getInstance().get(HOUR_OF_DAY);
    }

    @Override
    public int getMinute() {
      return java.util.Calendar.getInstance().get(MINUTE);
    }

  }

}
