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

import com.kuelye.notbadcoffee.model.Place;
import com.kuelye.notbadcoffee.model.Places;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlacesJsonParser extends AbstractJsonArrayParser<Places> {

  @Override
  @NonNull public Places parse(@NonNull JSONArray placesJsonArray) throws Exception {
    final PlaceJsonParser placeJsonParser = new PlaceJsonParser();
    final Places places = new Places();
    for (int i = 0; i < placesJsonArray.length(); ++i) {
      final JSONObject placeJsonObject = placesJsonArray.getJSONObject(i);
      final Place place = placeJsonParser.parse(placeJsonObject);
      places.add(place);
    }

    return places;
  }

}
