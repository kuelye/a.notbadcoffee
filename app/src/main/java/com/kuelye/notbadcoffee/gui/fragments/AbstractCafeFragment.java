package com.kuelye.notbadcoffee.gui.fragments;

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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.squareup.otto.Subscribe;

import static com.kuelye.notbadcoffee.Application.getBus;

public abstract class AbstractCafeFragment extends AbstractMapFragment {

  public static final String CAFE_PLACE_ID_EXTRA = "CAFE_PLACE_ID";

  @Nullable protected Cafe mCafe;

  protected static void putToBundle(@NonNull Bundle bundle, int cafePlaceId) {
    bundle.putInt(CAFE_PLACE_ID_EXTRA, cafePlaceId);
  }

  @Override
  public void onResume() {
    super.onResume();

    getBus().register(this);
    update();
  }

  @Override
  public void onPause() {
    super.onPause();

    getBus().unregister(this);
  }

  @Subscribe
  public void onCafeGotten(GetCafeAsyncTask.Event getCafeEvent) {
    mCafe = getCafeEvent.getCafe();
  }

  /* =========================== HIDDEN ============================= */

  protected int getCafePlaceId() {
    return getArguments().getInt(CAFE_PLACE_ID_EXTRA);
  }

  private void update() {
    new GetCafeAsyncTask().execute(getCafePlaceId());
  }

}
