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

import com.kuelye.notbadcoffee.model.CafePlace;
import com.kuelye.notbadcoffee.model.Location;

import org.json.JSONException;
import org.json.JSONObject;

public class CafePlaceJsonParser extends AbstractJsonParser<CafePlace> {

  private static final String ADDRESS_KEY_NAME = "address";
  private static final String METRO_KEY_NAME = "metro";
  private static final String LOCATION_KEY_NAME = "location";

  @Override
  @Nullable public CafePlace parse(@NonNull JSONObject cafePlaceJsonObject)
      throws JSONException {
    final String address = cafePlaceJsonObject.getString(ADDRESS_KEY_NAME);
    final String metro = cafePlaceJsonObject.getString(METRO_KEY_NAME);
    final JSONObject locationJsonObject = cafePlaceJsonObject.getJSONObject(LOCATION_KEY_NAME);
    final Location location = new LocationJsonParser().parse(locationJsonObject);

    return new CafePlace.Builder(address, metro, location).build();
  }

}
