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

import java.util.List;

public class Cafe {

  @NonNull private final String mName;
  @NonNull private final List<CafePlace> mPlaces;

  private Cafe(Builder builder) {
    mName = builder.mName;
    mPlaces = builder.mPlaces;
  }

  @NonNull public String getName() {
    return mName;
  }

  @NonNull public List<CafePlace> getPlaces() {
    return mPlaces;
  }

  @Override
  public String toString() {
    return "Cafe{" +
        "mName='" + mName + '\'' +
        ", mPlaces=" + mPlaces +
        '}';
  }

  /* =========================== INNER ============================== */

  public static class Builder {

    @NonNull private final String mName;
    @NonNull private final List<CafePlace> mPlaces;

    public Builder(@NonNull String name, @NonNull List<CafePlace> places) {
      mName = name;
      mPlaces = places;
    }

    public Cafe build() {
      return new Cafe(this);
    }

  }

}
