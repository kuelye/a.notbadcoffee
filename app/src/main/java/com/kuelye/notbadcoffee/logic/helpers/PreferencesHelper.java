package com.kuelye.notbadcoffee.logic.helpers;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesHelper {

  private static final String PREFERENCES_FILE_NAME = "com.kuelye.notbadcoffee.PREFERENCES";

  private static final String LAST_UPDATE_CAFES_TIME_KEY = "LAST_UPDATE_CAFES_TIME";

  public static final long LAST_UPDATE_CAFES_TIME_DEFAULT = -1L;

  public static long getLastUpdateCafesTime(@NonNull Context context) {
    final SharedPreferences preferences
        = context.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);

    return preferences.getLong(LAST_UPDATE_CAFES_TIME_KEY, LAST_UPDATE_CAFES_TIME_DEFAULT);
  }

  public static void setLastUpdateCafesTime(@NonNull Context context, long lastUpdateCafesTime) {
    final SharedPreferences preferences
        = context.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE);
    final Editor editor = preferences.edit();
    editor.putLong(LAST_UPDATE_CAFES_TIME_KEY, lastUpdateCafesTime);
    editor.apply();
  }

}
