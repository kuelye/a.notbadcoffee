package com.kuelye.notbadcoffee.logic.operations;

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

import com.kuelye.notbadcoffee.Application;
import com.kuelye.notbadcoffee.logic.parsers.json.CafesJsonParser;
import com.kuelye.notbadcoffee.model.Cafes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import static com.kuelye.components.utils.NetworkUtils.getResponse;
import static com.kuelye.notbadcoffee.ProjectConfig.GET_CAFES_REQUEST;

public class UpdateCafesOperation implements Callable<Cafes> {

  private static final String CAFES_KEY_NAME = "cafes";

  @Override
  public Cafes call() throws Exception {
    final String response = getResponse(GET_CAFES_REQUEST);
    final JSONObject responseJsonObject = new JSONObject(response);
    final JSONArray cafesJsonArray = responseJsonObject.getJSONArray(CAFES_KEY_NAME);
    final Cafes cafes = new CafesJsonParser().parse(cafesJsonArray);

    Application.setCafes(cafes);

    return cafes;
  }

}