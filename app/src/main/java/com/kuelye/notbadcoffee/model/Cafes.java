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

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.kuelye.notbadcoffee.Application.getLastLocation;
import static java.lang.Math.signum;

public class Cafes extends ArrayList<Cafe> {

  public Cafes split() {
    final Cafes cafes = new Cafes();
    for (Cafe cafe : this) {
      cafes.addAll(cafe.split());
    }

    return cafes;
  }

  public int positionByPlaceId(long placeId) {
    for (int i = 0; i < this.size(); ++i) {
      if (get(i).getPlace().getId() == placeId) {
        return i;
      }
    }

    return -1;
  }

  public Cafe byPlaceId(long placeId) {
    for (Cafe cafe : this) {
      for (Place place : cafe.getPlaces()) {
        if (place.getId() == placeId) {
          return cafe;
        }
      }
    }

    return null;
  }

  /**
   * Closed cafes are in the end of the list.
   * Others are sorted by the distance in the ascending order.
   */
  public void sort() {
    final Location location = getLastLocation();
    if (location != null) {
      Collections.sort(this, new Comparator<Cafe>() {
        @Override
        public int compare(Cafe lhs, Cafe rhs) {
          final boolean isLhsClosed = lhs.getTimetable() != null && !lhs.getTimetable().isOpened();
          final boolean isRhsClosed = rhs.getTimetable() != null && !rhs.getTimetable().isOpened();
          if (isLhsClosed && !isRhsClosed) {
            return 1;
          } else if (!isLhsClosed && isRhsClosed) {
            return -1;
          } else {
            final Location lhsLocation = lhs.getPlace().getLocation().toLocation();
            final double lhsDistance = location.distanceTo(lhsLocation);
            final Location rhsLocation = rhs.getPlace().getLocation().toLocation();
            final double rhsDistance = location.distanceTo(rhsLocation);

            return (int) signum(lhsDistance - rhsDistance);
          }
        }
      });
    }
  }

}
