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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Bus;

public class Application extends android.app.Application {

  @NonNull private static final Bus sBus = new Bus();

  @Nullable private static Cafes sCafes;

  @NonNull public static Bus getBus() {
    return sBus;
  }

  @Nullable public static Cafes getCafes() {
    return sCafes;
  }

  public static void setCafes(@Nullable Cafes cafes) {
    sCafes = cafes;
  }

}
