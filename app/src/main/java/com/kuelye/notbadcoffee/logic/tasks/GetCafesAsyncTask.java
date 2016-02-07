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

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kuelye.notbadcoffee.logic.operations.GetCafesOperation;
import com.kuelye.notbadcoffee.model.Cafes;

import java.lang.ref.WeakReference;

import static com.kuelye.notbadcoffee.Application.getBus;

public class GetCafesAsyncTask extends AsyncTask<Boolean, Void, Cafes> {

  public static final String TAG = "GetCafesAsyncTask";

  @NonNull private final WeakReference<Context> mContextReference;

  public GetCafesAsyncTask(@NonNull Context context) {
    mContextReference = new WeakReference<>(context);
  }

  @Override
  protected Cafes doInBackground(Boolean... params) {
    boolean networkForced = params[0];
    try {
      return new GetCafesOperation(mContextReference, networkForced).call();
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
