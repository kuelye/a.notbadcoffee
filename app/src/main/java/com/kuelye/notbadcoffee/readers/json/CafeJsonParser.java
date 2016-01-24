package com.kuelye.notbadcoffee.readers.json;

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
import android.support.annotation.Nullable;
import android.util.Log;

import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.CafePlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CafeJsonParser extends AbstractJsonParser<Cafe> {

  private static final String NAME_KEY_NAME = "name";
  private static final String PLACES_KEY_NAME = "places";

  @Override
  @Nullable public Cafe parse(@NonNull JSONObject cafeJsonObject)
      throws JSONException {
    final String name = cafeJsonObject.getString(NAME_KEY_NAME);
    final JSONArray cafePlacesJsonArray = cafeJsonObject.getJSONArray(PLACES_KEY_NAME);
    final CafePlaceJsonParser cafePlaceJsonParser = new CafePlaceJsonParser();
    final List<CafePlace> cafePlaces = new ArrayList<>();
    for (int i = 0; i < cafePlacesJsonArray.length(); ++i) {
      final JSONObject cafePlaceJsonObject = cafePlacesJsonArray.getJSONObject(i);
      final CafePlace cafePlace = cafePlaceJsonParser.parse(cafePlaceJsonObject);
      cafePlaces.add(cafePlace);
    }

    return new Cafe.Builder(name, cafePlaces).build();
  }

}
