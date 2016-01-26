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

import com.kuelye.notbadcoffee.model.CafeTimetable;
import com.kuelye.notbadcoffee.model.CafeTimetableRow;

import org.json.JSONArray;
import org.json.JSONObject;

public class CafeTimetableJsonParser extends AbstractJsonArrayParser<CafeTimetable> {

  @Override
  @NonNull public CafeTimetable parse(@NonNull JSONArray cafeTimetableJsonArray) throws Exception {
    final CafeTimetableRowJsonParser timetableRowJsonParser = new CafeTimetableRowJsonParser();
    final CafeTimetable timetable = new CafeTimetable();
    for (int i = 0; i < cafeTimetableJsonArray.length(); ++i) {
      final JSONObject timetableRowJsonObject = cafeTimetableJsonArray.getJSONObject(i);
      final CafeTimetableRow timetableRow = timetableRowJsonParser.parse(timetableRowJsonObject);
      timetable.add(timetableRow);
    }

    return timetable;
  }

}
