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
import com.kuelye.notbadcoffee.model.Cafes;

import org.json.JSONArray;
import org.json.JSONObject;

public class CafesJsonParser extends AbstractJsonArrayParser<Cafes> {

  @Override
  @NonNull public Cafes parse(@NonNull JSONArray cafesJsonArray) throws Exception {
    final CafeJsonParser cafeJsonParser = new CafeJsonParser();
    final Cafes cafes = new Cafes();
    for (int i = 0; i < cafesJsonArray.length(); ++i) {
      final JSONObject cafeJsonObject = cafesJsonArray.getJSONObject(i);
      final Cafe cafe = cafeJsonParser.parse(cafeJsonObject);
      cafes.add(cafe);
    }

    return cafes;
  }

}