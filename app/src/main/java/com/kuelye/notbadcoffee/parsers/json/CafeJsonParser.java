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

import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.CafePlace;
import com.kuelye.notbadcoffee.model.CafePlaces;
import com.kuelye.notbadcoffee.model.CafeTimetable;
import com.kuelye.notbadcoffee.model.CafeTimetableRow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CafeJsonParser extends AbstractJsonObjectParser<Cafe> {

  private static final String NAME_KEY_NAME = "name";
  private static final String PLACES_KEY_NAME = "places";
  private static final String TIMETABLE_KEY_NAME = "timetable";

  @Override
  @NonNull public Cafe parse(@NonNull JSONObject cafeJsonObject)
      throws Exception {
    final String name = cafeJsonObject.getString(NAME_KEY_NAME);
    final CafePlaces places = new CafePlacesJsonParser()
        .parse(cafeJsonObject.getJSONArray(PLACES_KEY_NAME));
    final CafeTimetable timetable
        = cafeJsonObject.has(TIMETABLE_KEY_NAME)
        ? new CafeTimetableJsonParser()
            .parse(cafeJsonObject.getJSONArray(TIMETABLE_KEY_NAME))
        : null;

    return new Cafe.Builder(name, places)
        .setTimetable(timetable)
        .build();
  }

}
