package com.kuelye.notbadcoffee.logic.parsers.string;

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

import com.kuelye.notbadcoffee.model.DayRange;
import com.kuelye.notbadcoffee.logic.parsers.UnsupportedParseValue;

import static com.kuelye.notbadcoffee.model.DayRange.EVERYDAY;
import static com.kuelye.notbadcoffee.model.DayRange.WEEKDAYS;
import static com.kuelye.notbadcoffee.model.DayRange.WEEKEND;

public class DayRangeStringParser extends AbstractStringParser<DayRange> {

  private static final String WEEKDAYS_VALUE = "WEEKDAYS";
  private static final String WEEKEND_VALUE = "WEEKEND";
  private static final String EVERYDAY_VALUE = "EVERYDAY";

  @Override
  @NonNull public DayRange parse(@NonNull String dayRangeAsString) throws Exception {
    switch (dayRangeAsString) {
      case WEEKDAYS_VALUE:
        return new DayRange(WEEKDAYS);
      case WEEKEND_VALUE:
        return new DayRange(WEEKEND);
      case EVERYDAY_VALUE:
        return new DayRange(EVERYDAY);
      // TODO "SUN, MON", "MON - SAT", "TUE - THU, WEEKEND"
      default:
        throw new UnsupportedParseValue(dayRangeAsString);
    }
  }

}
