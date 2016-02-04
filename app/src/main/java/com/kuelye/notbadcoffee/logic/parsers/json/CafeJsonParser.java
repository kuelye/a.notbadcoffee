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

import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Menu;
import com.kuelye.notbadcoffee.model.Places;
import com.kuelye.notbadcoffee.model.Timetable;

import org.json.JSONObject;

public class CafeJsonParser extends AbstractJsonObjectParser<Cafe> {

  private static final String NAME_KEY_NAME = "name";
  private static final String PLACES_KEY_NAME = "places";
  private static final String MENU_KEY_NAME = "menu";

  @Override
  @NonNull public Cafe parse(@NonNull JSONObject cafeJsonObject)
      throws Exception {
    final String name = cafeJsonObject.getString(NAME_KEY_NAME);
    final Places places = new PlacesJsonParser()
        .parse(cafeJsonObject.getJSONArray(PLACES_KEY_NAME));
    final Menu menu
        = cafeJsonObject.has(MENU_KEY_NAME)
        ? new MenuJsonParser()
            .parse(cafeJsonObject.getJSONArray(MENU_KEY_NAME))
        : null;

    return new Cafe.Builder(name, places)
        .setMenu(menu)
        .build();
  }

}
