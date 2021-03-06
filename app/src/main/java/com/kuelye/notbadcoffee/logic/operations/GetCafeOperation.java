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

import android.content.Context;
import android.support.annotation.NonNull;

import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

public class GetCafeOperation implements Callable<Cafe> {

  @NonNull private final WeakReference<Context> mContextReference;
  private final long mPlaceId;

  public GetCafeOperation(@NonNull WeakReference<Context> contextReference, long placeId) {
    mContextReference = contextReference;
    mPlaceId = placeId;
  }

  @Override
  public Cafe call() throws Exception {
    final Cafes cafes = new GetCafesOperation(mContextReference, false).call();
    if (cafes != null) {
      return cafes.byPlaceId(mPlaceId);
    } else {
      return null;
    }
  }

}
