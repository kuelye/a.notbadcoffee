package com.kuelye.components.utils;

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

import android.animation.Animator;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import static android.util.TypedValue.complexToDimensionPixelSize;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public final class AndroidUtils {

  public static int getStatusBarHeight(@NonNull Context context) {
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

    return resourceId > 0
        ? context.getResources().getDimensionPixelSize(resourceId)
        : 0;
  }

  public static int getActionBarHeight(@NonNull Context context) {
    TypedValue actionBarSize = new TypedValue();
    context.getTheme().resolveAttribute(
        android.support.v7.appcompat.R.attr.actionBarSize, actionBarSize, true);

    return complexToDimensionPixelSize(actionBarSize.data
        , context.getResources().getDisplayMetrics());
  }

  public static int getRelativeTop(@NonNull View view) {
    if (view.getParent() == view.getRootView()) {
      return view.getTop();
    } else {
      return view.getTop() + getRelativeTop((View) view.getParent());
    }
  }

  public static LatLng locationToLatLng(@NonNull Location location) {
    return new LatLng(location.getLatitude(), location.getLongitude());
  }

  /* ========================== INNER =============================== */

  public static class AnimatorListenerStub implements Animator.AnimatorListener {

    @Override
    public void onAnimationStart(Animator animation) {
      // stub
    }

    @Override
    public void onAnimationEnd(Animator animation) {
      // stub
    }

    @Override
    public void onAnimationCancel(Animator animation) {
      // stub
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
      // stub
    }

  }

}
