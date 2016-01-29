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

import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;

import java.util.concurrent.Callable;

import static com.kuelye.notbadcoffee.Application.getCafes;

public class GetCafeOperation implements Callable<Cafe> {

  private final int mPlaceId;

  public GetCafeOperation(int placeId) {
    mPlaceId = placeId;
  }

  @Override
  public Cafe call() throws Exception {
    Cafes cafes = getCafes();
    if (cafes == null) {
      cafes = new UpdateCafesOperation().call();
    }

    return cafes.byPlaceId(mPlaceId);
  }

}
