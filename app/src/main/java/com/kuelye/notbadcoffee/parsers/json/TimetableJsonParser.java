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

import com.kuelye.notbadcoffee.model.Timetable;
import com.kuelye.notbadcoffee.model.TimetableRow;

import org.json.JSONArray;
import org.json.JSONObject;

public class TimetableJsonParser extends AbstractJsonArrayParser<Timetable> {

  @Override
  @NonNull public Timetable parse(@NonNull JSONArray timetableJsonArray) throws Exception {
    final TimetableRowJsonParser timetableRowJsonParser = new TimetableRowJsonParser();
    final Timetable timetable = new Timetable();
    for (int i = 0; i < timetableJsonArray.length(); ++i) {
      final JSONObject timetableRowJsonObject = timetableJsonArray.getJSONObject(i);
      final TimetableRow timetableRow = timetableRowJsonParser.parse(timetableRowJsonObject);
      timetable.add(timetableRow);
    }

    return timetable;
  }

}
