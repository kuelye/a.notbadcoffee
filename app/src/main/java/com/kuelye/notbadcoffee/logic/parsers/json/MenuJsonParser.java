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

import com.kuelye.notbadcoffee.model.Menu;
import com.kuelye.notbadcoffee.model.MenuRow;

import org.json.JSONArray;
import org.json.JSONObject;

public class MenuJsonParser extends AbstractJsonArrayParser<Menu> {

  @Override
  @NonNull public Menu parse(@NonNull JSONArray menuJsonArray) throws Exception {
    final MenuRowJsonParser menuRowJsonParser = new MenuRowJsonParser();
    final Menu menu = new Menu();
    for (int i = 0; i < menuJsonArray.length(); ++i) {
      final JSONObject menuRowJsonObject = menuJsonArray.getJSONObject(i);
      final MenuRow menuRow = menuRowJsonParser.parse(menuRowJsonObject);
      menu.add(menuRow);
    }

    return menu;
  }

}
