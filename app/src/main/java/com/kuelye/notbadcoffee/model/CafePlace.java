package com.kuelye.notbadcoffee.model;

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

import android.support.annotation.NonNull;

public class CafePlace {

  @NonNull private final String mAddress;
  @NonNull private final String mMetro;
  @NonNull private final Location mLocation;

  private CafePlace(Builder builder) {
    mAddress = builder.mAddress;
    mMetro = builder.mMetro;
    mLocation = builder.mLocation;
  }

  @NonNull public String getAddress() {
    return mAddress;
  }

  @NonNull public String getMetro() {
    return mMetro;
  }

  @NonNull public Location getLocation() {
    return mLocation;
  }

  @Override
  public String toString() {
    return "CafePlace{" +
        "mAddress='" + mAddress + '\'' +
        ", mMetro='" + mMetro + '\'' +
        ", mLocation=" + mLocation +
        '}';
  }

  /* =========================== INNER ============================== */

  public static class Builder {

    @NonNull private final String mAddress;
    @NonNull private final String mMetro;
    @NonNull private final Location mLocation;

    public Builder(@NonNull String address, @NonNull String metro, @NonNull Location location) {
      mAddress = address;
      mMetro = metro;
      mLocation = location;
    }

    public CafePlace build() {
      return new CafePlace(this);
    }

  }

}
