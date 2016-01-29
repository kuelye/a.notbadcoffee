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

import java.util.ArrayList;

public class Cafes extends ArrayList<Cafe> {

  public Cafe byPlaceId(int placeId) {
    for (Cafe cafe : this) {
      for (Place place : cafe.getPlaces()) {
        if (place.getId() == placeId) {
          return cafe;
        }
      }
    }

    return null;
  }

}