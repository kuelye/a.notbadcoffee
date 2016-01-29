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

import com.kuelye.notbadcoffee.model.Location;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationJsonParser extends AbstractJsonObjectParser<Location> {

  private static final String LATITUDE_KEY_NAME = "latitude";
  private static final String LONGITUDE_KEY_NAME = "longitude";

  @Override
  @NonNull public Location parse(@NonNull JSONObject locationJsonObject)
      throws JSONException {
    final double latitude = locationJsonObject.getDouble(LATITUDE_KEY_NAME);
    final double longitude = locationJsonObject.getDouble(LONGITUDE_KEY_NAME);

    return new Location(latitude, longitude);
  }

}
