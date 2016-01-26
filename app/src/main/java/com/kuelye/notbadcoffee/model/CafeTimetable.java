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

import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.format;
import static java.util.Calendar.DAY_OF_WEEK;

public class CafeTimetable extends ArrayList<CafeTimetableRow> {

  // TODO 12h (am/pm) format support
  @NonNull public String getOpenUntilDisplayString(@NonNull Context context) {
    final int day = Calendar.getInstance().get(DAY_OF_WEEK);
    for (CafeTimetableRow timetableRow : this) {
      if (timetableRow.getDayRange().isIncludeDay(day)) {
        final String openUntilTemplate = context.getString(R.string.cafe_row_open_until_template);
        String timeTo = timetableRow.getTimeRange().getTimeTo();
        if (timeTo.equals(TimeRange.MIDNIGHT_TIME)) {
          timeTo = context.getString(R.string.cafe_row_midnight);
        }

        return format(openUntilTemplate, timeTo);
      }
    }

    return context.getString(R.string.cafe_row_closed);
  }

}
