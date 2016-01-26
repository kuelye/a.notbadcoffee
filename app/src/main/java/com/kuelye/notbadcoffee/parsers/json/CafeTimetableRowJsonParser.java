package com.kuelye.notbadcoffee.parsers.json;

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

import com.kuelye.notbadcoffee.model.CafeTimetableRow;
import com.kuelye.notbadcoffee.model.DayRange;
import com.kuelye.notbadcoffee.model.TimeRange;
import com.kuelye.notbadcoffee.parsers.string.DayRangeStringParser;
import com.kuelye.notbadcoffee.parsers.string.TimeRangeStringParser;

import org.json.JSONObject;

public class CafeTimetableRowJsonParser extends AbstractJsonObjectParser<CafeTimetableRow> {

  private static final String DAY_RANGE_KEY_NAME = "dayRange";
  private static final String TIME_RANGE_KEY_NAME = "timeRange";

  @Override
  @NonNull public CafeTimetableRow parse(@NonNull JSONObject cafeTimetableRowJsonObject)
      throws Exception {
    final String dayRangeAsString = cafeTimetableRowJsonObject.getString(DAY_RANGE_KEY_NAME);
    final DayRange dayRange = new DayRangeStringParser().parse(dayRangeAsString);
    final String timeRangeAsString = cafeTimetableRowJsonObject.getString(TIME_RANGE_KEY_NAME);
    final TimeRange timeRange = new TimeRangeStringParser().parse(timeRangeAsString);

    return new CafeTimetableRow(dayRange, timeRange);
  }

}
