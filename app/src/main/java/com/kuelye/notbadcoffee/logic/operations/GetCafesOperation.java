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
import android.support.annotation.Nullable;
import android.util.Log;

import com.kuelye.components.utils.IOUtils;
import com.kuelye.notbadcoffee.Application;
import com.kuelye.notbadcoffee.logic.parsers.json.CafesJsonParser;
import com.kuelye.notbadcoffee.model.Cafes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import static com.kuelye.components.utils.IOUtils.saveAsTempFileSilently;
import static com.kuelye.components.utils.NetworkUtils.getResponse;
import static com.kuelye.components.utils.StringUtils.ENCODING_DEFAULT;
import static com.kuelye.notbadcoffee.Application.setCafes;
import static com.kuelye.notbadcoffee.ProjectConfig.GET_CAFES_REQUEST;
import static com.kuelye.notbadcoffee.logic.helpers.CalendarHelper.getCalendar;
import static com.kuelye.notbadcoffee.logic.helpers.PreferencesHelper.getLastUpdateCafesTime;
import static com.kuelye.notbadcoffee.logic.helpers.PreferencesHelper.setLastUpdateCafesTime;

public class GetCafesOperation implements Callable<Cafes> {

  public static final String TAG = "GetCafesOperation";

  private static final long UPDATE_CAFES_MINIMUM_DELAY_IN_MILLIS = 60 * 1000;
  private static final String CAFES_KEY_NAME = "cafes";
  private static final String FILE_NAME = "cafes-cache";

  @NonNull private final WeakReference<Context> mContextReference;
  private final boolean mNetworkForced;

  public GetCafesOperation(@NonNull WeakReference<Context> contextReference, boolean networkForced) {
    mContextReference = contextReference;
    mNetworkForced = networkForced;
  }

  @Override
  public Cafes call() throws Exception {
    boolean networkForced = mNetworkForced;
    if (mContextReference.get() != null) {
      final long lastCafesUpdateTime = getLastUpdateCafesTime(mContextReference.get());
      if (getCalendar().getTimeInMillis() - lastCafesUpdateTime >= UPDATE_CAFES_MINIMUM_DELAY_IN_MILLIS) {
        networkForced = true;
      }
    }

    Source[] sources = new Source[]{ Source.MEMORY, Source.FILE, Source.NETWORK };
    if (networkForced) {
      sources = new Source[]{ Source.NETWORK, Source.MEMORY, Source.FILE };
    }

    final Cafes cafes = getCafes(sources);
    if (cafes != null) {
      cafes.sortByDistanceAscending();
    }

    return cafes;
  }

  /* ========================== HIDDEN ============================== */

  @Nullable private Cafes getCafes(@NonNull Source... sources) {
    for (Source source : sources) {
      final Cafes cafes = getCafesFromSource(source);
      if (cafes != null) {
        return cafes;
      }
    }

    return null;
  }

  @Nullable private Cafes getCafesFromSource(@NonNull Source source) {
    switch (source) {
      case NETWORK:
        return getCafesFromNetwork();
      case MEMORY:
        return Application.getCafes();
      case FILE:
        return getCafesFromFile();
      default:
        return null;
    }
  }

  @Nullable private Cafes getCafesFromNetwork() {
    try {
      final String response = getResponse(GET_CAFES_REQUEST);
      final Cafes cafes = parse(response);
      setCafes(cafes);
      if (mContextReference.get() != null) {
        setLastUpdateCafesTime(mContextReference.get(), getCalendar().getTimeInMillis());
        saveAsTempFileSilently(mContextReference.get(), FILE_NAME, response);
      }

      return cafes;
    } catch (Exception e) {
      Log.e(TAG, "", e);

      return null;
    }
  }

  @Nullable private Cafes getCafesFromFile() {
    if (mContextReference.get() != null) {
      try {
        final File file = new File(mContextReference.get().getCacheDir(), FILE_NAME);
        final InputStream is = new FileInputStream(file);
        final String response = IOUtils.readFullyAndCloseSilently(is, ENCODING_DEFAULT);

        return parse(response);
      } catch (Exception e) {
        Log.e(TAG, "", e);

        return null;
      }
    } else {
      return null;
    }
  }

  @NonNull public Cafes parse(@NonNull String response) throws Exception {
    final JSONObject responseJsonObject = new JSONObject(response);
    final JSONArray cafesJsonArray = responseJsonObject.getJSONArray(CAFES_KEY_NAME);

    return new CafesJsonParser().parse(cafesJsonArray).split();
  }

  /* ========================== INNER =============================== */

  private enum Source {

    NETWORK
    , MEMORY
    , FILE

  }

}
