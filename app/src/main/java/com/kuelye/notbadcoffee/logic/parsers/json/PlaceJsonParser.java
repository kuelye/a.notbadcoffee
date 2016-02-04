package com.kuelye.notbadcoffee.logic.parsers.json;

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

import com.kuelye.notbadcoffee.model.Place;
import com.kuelye.notbadcoffee.model.Location;
import com.kuelye.notbadcoffee.model.Timetable;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceJsonParser extends AbstractJsonObjectParser<Place> {

  private static final String ID_KEY_NAME = "id";
  private static final String ADDRESS_KEY_NAME = "address";
  private static final String METRO_KEY_NAME = "metro";
  private static final String LOCATION_KEY_NAME = "location";
  private static final String NAME_KEY_NAME = "name";
  private static final String PHOTO_KEY_NAME = "photo";
  private static final String TIMETABLE_KEY_NAME = "timetable";

  @Override
  @NonNull public Place parse(@NonNull JSONObject placeJsonObject)
      throws Exception {
    final int id = placeJsonObject.getInt(ID_KEY_NAME);
    final String address = placeJsonObject.getString(ADDRESS_KEY_NAME);
    final String metro = placeJsonObject.getString(METRO_KEY_NAME);
    final JSONObject locationJsonObject = placeJsonObject.getJSONObject(LOCATION_KEY_NAME);
    final Location location = new LocationJsonParser().parse(locationJsonObject);
    final String name
        = placeJsonObject.has(NAME_KEY_NAME)
        ? placeJsonObject.getString(NAME_KEY_NAME)
        : null;
    final String photo
        = placeJsonObject.has(PHOTO_KEY_NAME)
        ? placeJsonObject.getString(PHOTO_KEY_NAME)
        : null;
    final Timetable timetable
        = placeJsonObject.has(TIMETABLE_KEY_NAME)
        ? new TimetableJsonParser().parse(placeJsonObject.getJSONArray(TIMETABLE_KEY_NAME))
        : null;

    return new Place.Builder(id, address, metro, location)
        .setName(name)
        .setPhoto(photo)
        .setTimetable(timetable)
        .build();
  }

}
