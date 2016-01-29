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

import com.kuelye.notbadcoffee.model.TimeRange;
import com.kuelye.notbadcoffee.logic.parsers.UnsupportedParseValue;

import static com.kuelye.notbadcoffee.model.TimeRange.SEPARATOR;

public class TimeRangeStringParser extends AbstractStringParser<TimeRange> {

  @Override
  @NonNull public TimeRange parse(@NonNull String timeRangeAsString) throws Exception {
    final String[] parts = timeRangeAsString.split(SEPARATOR);

    if (parts.length != 2) {
      throw new UnsupportedParseValue(timeRangeAsString);
    } else {
      return new TimeRange(parts[0].trim(), parts[1].trim());
    }
  }

}
