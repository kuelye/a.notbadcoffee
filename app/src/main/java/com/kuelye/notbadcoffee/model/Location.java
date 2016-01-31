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

import com.google.android.gms.maps.model.LatLng;

public class Location {

  private double mLatitude;
  private double mLongitude;

  public Location(double latitude, double longitude) {
    mLatitude = latitude;
    mLongitude = longitude;
  }

  public LatLng toLatLng() {
    return new LatLng(mLatitude, mLongitude);
  }

  public double getLatitude() {
    return mLatitude;
  }

  public double getLongitude() {
    return mLongitude;
  }

  @Override
  public String toString() {
    return "Location{" +
        "mLatitude=" + mLatitude +
        ", mLongitude=" + mLongitude +
        '}';
  }

}
