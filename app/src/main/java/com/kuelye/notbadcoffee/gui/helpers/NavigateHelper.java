package com.kuelye.notbadcoffee.gui.helpers;

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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.activities.CafeActivity;
import com.kuelye.notbadcoffee.gui.activities.MapActivity;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.model.Cafe;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static android.view.Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME;
import static android.view.Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME;
import static com.kuelye.notbadcoffee.gui.fragments.MapFragment.CAFE_PLACE_ID_EXTRA;

public final class NavigateHelper {

  public static void launchMapActivity(
      @NonNull Activity activityFrom
      , @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    Bundle options = null;
    if (SDK_INT >= LOLLIPOP) {
      options = setTransitionNameAndGetOptions(activityFrom
          // share toolbar & stub view to avoid overlaying
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar_background)
              , R.string.toolbar_background_transition_name)
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar)
              , R.string.toolbar_transition_name)
          , new SharedElementHolder(cafeRowViewHolder.rootView
              , R.string.card_view_transition_name))
          .toBundle();
    }

    final Intent intent = new Intent(activityFrom, MapActivity.class);
    intent.putExtra(CAFE_PLACE_ID_EXTRA, cafe.getPlace().getId());

    ActivityCompat.startActivity(activityFrom, intent, options);
  }

  public static void launchCafeActivity(
      @NonNull Activity activityFrom
      , @NonNull View view
      , int cafePlaceId) {
    Bundle options = null;
    if (SDK_INT >= LOLLIPOP) {
      options = setTransitionNameAndGetOptions(activityFrom
          // share toolbar & stub view to avoid overlaying
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar_background)
              , R.string.toolbar_background_transition_name)
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar)
              , R.string.toolbar_transition_name)
          , new SharedElementHolder(view
              , R.string.cafe_photo_image_view_transition_name))
          .toBundle();
    }

    final Intent intent = new Intent(activityFrom, CafeActivity.class);
    intent.putExtra(CAFE_PLACE_ID_EXTRA, cafePlaceId);

    ActivityCompat.startActivity(activityFrom, intent, options);
  }

  public static void setTransitionName(
      @NonNull Context context
      , @Nullable View view
      , @StringRes int transitionNameResource) {
    if (view != null) {
      final String transitionName = context.getString(transitionNameResource);
      ViewCompat.setTransitionName(view, transitionName);
    }
  }

  /* ========================= HIDDEN =============================== */

  @SuppressWarnings("unchecked")
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private static ActivityOptionsCompat setTransitionNameAndGetOptions(
      @NonNull Activity activityFrom
      , SharedElementHolder... sharedElementHolders) {
    List<Pair<View, String>> sharedElements = new ArrayList<>();
    if (sharedElementHolders != null) {
      for (SharedElementHolder sharedElementHolder : sharedElementHolders) {
        if (sharedElementHolder.view != null) {
          final String transitionName = activityFrom.getString(sharedElementHolder.transitionNameResource);
          ViewCompat.setTransitionName(sharedElementHolder.view, transitionName);
          sharedElements.add(Pair.create(sharedElementHolder.view, transitionName));
        }
      }
    }

    // share status & navigation bar to avoid overlaying
    final View decorView = activityFrom.getWindow().getDecorView();
    final View statusBarBackgroundView = decorView.findViewById(android.R.id.statusBarBackground);
    if (statusBarBackgroundView != null) {
      sharedElements.add(Pair.create(statusBarBackgroundView, STATUS_BAR_BACKGROUND_TRANSITION_NAME));
    }
    final View navigationBarBackgroundView = decorView.findViewById(android.R.id.navigationBarBackground);
    if (navigationBarBackgroundView != null) {
      sharedElements.add(Pair.create(navigationBarBackgroundView, NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
    }

    return makeSceneTransitionAnimation(activityFrom
        , sharedElements.toArray(new Pair[sharedElements.size()]));
  }

  /* ========================= INNER ================================ */

  private static class SharedElementHolder {

    @Nullable public final View view;
    @StringRes public final int transitionNameResource;

    public SharedElementHolder(@Nullable View view, @StringRes int transitionNameResource) {
      this.view = view;
      this.transitionNameResource = transitionNameResource;
    }

  }

}
