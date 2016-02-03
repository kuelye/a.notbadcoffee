package com.kuelye.notbadcoffee;

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

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Bus;

public class Application extends android.app.Application {

  @NonNull private static final Bus sBus = new Bus();
  private static LruCache<String, Drawable> sDrawableCache
      = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

  @Nullable private static Cafes sCafes;
  @Nullable private static Location sLastLocation;

  @NonNull public static Bus getBus() {
    return sBus;
  }

  public static void putDrawableToCache(@NonNull String key, @Nullable Drawable drawable) {
    if (drawable == null) {
      sDrawableCache.remove(key);
    } else {
      sDrawableCache.put(key, drawable);
    }
  }

  @Nullable public static Drawable getDrawableFromCache(@NonNull String key) {
    return sDrawableCache.get(key);
  }

  @Nullable public static Drawable popDrawableFromCache(@NonNull String key) {
    final Drawable drawable = sDrawableCache.get(key);
    sDrawableCache.remove(key);

    return drawable;
  }

  @Nullable public static Cafes getCafes() {
    return sCafes;
  }

  public static void setCafes(@Nullable Cafes cafes) {
    sCafes = cafes;
  }

  @Nullable public static Location getLastLocation() {
    return sLastLocation;
  }

  public static void setLastLocation(@Nullable Location lastLocation) {
    sLastLocation = lastLocation;
  }

}
