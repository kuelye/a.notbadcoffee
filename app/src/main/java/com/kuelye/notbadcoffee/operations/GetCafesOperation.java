package com.kuelye.notbadcoffee.operations;

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

import com.kuelye.components.concurrent.AbstractOperation;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.parsers.json.CafesJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.kuelye.components.utils.NetworkUtils.getResponse;
import static com.kuelye.notbadcoffee.ProjectConfig.GET_CAFES_REQUEST;

public class GetCafesOperation extends AbstractOperation<List<Cafe>> {

  private static final String CAFES_KEY_NAME = "cafes";

  @Override
  public void doCall() throws Exception {
    final String response = getResponse(GET_CAFES_REQUEST);
    final JSONObject responseJsonObject = new JSONObject(response);
    final JSONArray cafesJsonArray = responseJsonObject.getJSONArray(CAFES_KEY_NAME);
    final List<Cafe> cafes = new CafesJsonParser().parse(cafesJsonArray);

    setResult(cafes);
  }

}