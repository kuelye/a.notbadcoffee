package com.kuelye.notbadcoffee.logic.tasks;

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

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kuelye.notbadcoffee.logic.operations.GetCafeOperation;
import com.kuelye.notbadcoffee.model.Cafe;

import static com.kuelye.notbadcoffee.Application.getBus;

public class GetCafeAsyncTask extends AsyncTask<Long, Void, Cafe> {

  public static final String TAG = "GetCafesAsyncTask";

  @Override
  protected Cafe doInBackground(Long... params) {
    try {
      final long placeId = params[0];
      return new GetCafeOperation(placeId).call();
    } catch (Exception e) {
      Log.e(TAG, "", e);
    }

    return null;
  }

  @Override protected void onPostExecute(Cafe cafe) {
    getBus().post(new Event(cafe));
  }

  /* ============================ INNER ============================= */

  public static class Event {

    @Nullable private final Cafe mCafe;

    public Event(@Nullable Cafe cafe) {
      mCafe = cafe;
    }

    @Nullable public Cafe getCafe() {
      return mCafe;
    }

  }

}
