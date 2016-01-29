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

import com.kuelye.notbadcoffee.logic.operations.GetCafesOperation;
import com.kuelye.notbadcoffee.model.Cafes;

import static com.kuelye.notbadcoffee.Application.getBus;

public class GetCafesAsyncTask extends AsyncTask<Void, Void, Cafes> {

  public static final String TAG = "GetCafesAsyncTask";

  @Override
  protected Cafes doInBackground(Void... params) {
    try {
      return new GetCafesOperation().call();
    } catch (Exception e) {
      Log.e(TAG, "", e);
    }

    return null;
  }

  @Override protected void onPostExecute(Cafes cafes) {
    getBus().post(new Event(cafes));
  }

  /* ============================ INNER ============================= */

  public static class Event {

    @Nullable private final Cafes mCafes;

    public Event(@Nullable Cafes cafes) {
      mCafes = cafes;
    }

    @Nullable public Cafes getCafes() {
      return mCafes;
    }

  }

}
