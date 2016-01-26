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
import android.support.annotation.Nullable;

public class Cafe {

  @NonNull private final String mName;
  @NonNull private final Places mPlaces;
  @Nullable private final Timetable mTimetable;
  @Nullable private final Menu mMenu;

  private Cafe(Builder builder) {
    mName = builder.mName;
    mPlaces = builder.mPlaces;
    mTimetable = builder.mTimetable;
    mMenu = builder.mMenu;
  }

  @NonNull public String getName() {
    return mName;
  }

  @NonNull public Places getPlaces() {
    return mPlaces;
  }

  @Nullable public Timetable getTimetable() {
    return mTimetable;
  }

  @Nullable public Menu getMenu() {
    return mMenu;
  }

  @Override
  public String toString() {
    return "Cafe{" +
        "mName='" + mName + '\'' +
        ", mPlaces=" + mPlaces +
        ", mTimetable=" + mTimetable +
        ", mMenu=" + mMenu +
        '}';
  }

  /* =========================== INNER ============================== */

  public static class Builder {

    @NonNull private final String mName;
    @NonNull private final Places mPlaces;
    @Nullable private Timetable mTimetable;
    @Nullable private Menu mMenu;

    public Builder(@NonNull String name, @NonNull Places places) {
      mName = name;
      mPlaces = places;
    }

    @NonNull public Builder setTimetable(@Nullable Timetable timetable) {
      mTimetable = timetable;

      return this;
    }

    @NonNull public Builder setMenu(@Nullable Menu menu) {
      mMenu = menu;

      return this;
    }

    public Cafe build() {
      return new Cafe(this);
    }

  }

}
