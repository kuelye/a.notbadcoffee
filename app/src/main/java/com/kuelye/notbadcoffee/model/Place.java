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

public class Place {

  public static final long STUB_ID = -1L;

  private final long mId;
  @NonNull private final String mAddress;
  @NonNull private final String mMetro;
  @NonNull private final Location mLocation;

  @Nullable private final String mName;
  @Nullable private final String mPhoto;
  @Nullable private final Timetable mTimetable;

  private Place(Builder builder) {
    mId = builder.mId;
    mAddress = builder.mAddress;
    mMetro = builder.mMetro;
    mLocation = builder.mLocation;
    mName = builder.mName;
    mPhoto = builder.mPhoto;
    mTimetable = builder.mTimetable;
  }

  public long getId() {
    return mId;
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

  @Nullable public String getName() {
    return mName;
  }

  @Nullable public String getPhoto() {
    return mPhoto;
  }

  @Nullable public Timetable getTimetable() {
    return mTimetable;
  }

  @Override
  public String toString() {
    return "Place{" +
        "mId=" + mId +
        ", mAddress='" + mAddress + '\'' +
        ", mMetro='" + mMetro + '\'' +
        ", mLocation=" + mLocation +
        ", mName='" + mName + '\'' +
        ", mPhoto='" + mPhoto + '\'' +
        ", mTimetable=" + mTimetable +
        '}';
  }

  /* =========================== INNER ============================== */

  public static class Builder {

    private final long mId;
    @NonNull private final String mAddress;
    @NonNull private final String mMetro;
    @NonNull private final Location mLocation;

    @Nullable private String mName;
    @Nullable private String mPhoto;
    @Nullable private Timetable mTimetable;

    public Builder(int id, @NonNull String address, @NonNull String metro, @NonNull Location location) {
      mId = id;
      mAddress = address;
      mMetro = metro;
      mLocation = location;
    }

    public Builder setPhoto(@Nullable String photo) {
      mPhoto = photo;

      return this;
    }

    @NonNull public Builder setTimetable(@Nullable Timetable timetable) {
      mTimetable = timetable;

      return this;
    }

    @NonNull public Builder setName(@Nullable String name) {
      mName = name;

      return this;
    }

    public Place build() {
      return new Place(this);
    }

  }

}
