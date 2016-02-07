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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ActionMenuView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.activities.CafeActivity;
import com.kuelye.notbadcoffee.gui.activities.MainActivity;
import com.kuelye.notbadcoffee.gui.activities.MapActivity;
import com.kuelye.notbadcoffee.model.Cafe;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static android.view.Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME;
import static android.view.Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME;
import static com.kuelye.notbadcoffee.Application.putDrawableToCache;
import static com.kuelye.notbadcoffee.gui.fragments.CafesFragment.SCROLL_TO_CAFE_PLACE_ID_EXTRA;
import static com.kuelye.notbadcoffee.gui.fragments.MapFragment.ENTER_CAFE_PLACE_ID_EXTRA;

public final class NavigateHelper {

  public static final String TRANSITION_CACHED_DRAWABLE_KEY = "TRANSITION_CACHED_DRAWABLE";
  public static final int SELECT_CAFE_REQUEST_CODE = 1;

  public static void launchMapActivity(
      @NonNull Activity activityFrom
      , @Nullable ViewGroup cafeLayout
      , @Nullable Cafe cafe) {
    Bundle options = null;
    if (SDK_INT >= LOLLIPOP) {
      options = setTransitionNameAndGetOptions(activityFrom
          // share toolbar & stub view to avoid overlaying
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar_background)
              , R.string.toolbar_background_transition_name)
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar)
              , R.string.toolbar_transition_name))
          .toBundle();

      activityFrom.getWindow().setExitTransition(new Slide(Gravity.START));
    }

    final Intent intent = new Intent(activityFrom, MapActivity.class);
    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
    if (cafe != null) {
      intent.putExtra(ENTER_CAFE_PLACE_ID_EXTRA, cafe.getPlace().getId());
    }
    if (cafeLayout != null) {
      final ImageView photoImageView = (ImageView) cafeLayout.findViewById(R.id.cafe_photo_image_view);
      putDrawableToCache(TRANSITION_CACHED_DRAWABLE_KEY, photoImageView.getDrawable());
    }

    startActivityForResult(activityFrom, intent, SELECT_CAFE_REQUEST_CODE, options);
  }

  public static void launchCafeActivity(
      @NonNull Activity activityFrom
      , @NonNull ViewGroup cafeHeaderLayout
      , @NonNull Cafe cafe) {
    Bundle options = null;
    final ViewGroup photoScrimLayout = (ViewGroup) cafeHeaderLayout.findViewById(R.id.cafe_photo_scrim_layout);
    final ImageView photoImageView = (ImageView) cafeHeaderLayout.findViewById(R.id.cafe_photo_image_view);
    final TextView nameTextView = (TextView) cafeHeaderLayout.findViewById(R.id.cafe_name_text_view);
    final ActionMenuView linksActionMenuView = (ActionMenuView) cafeHeaderLayout.findViewById(R.id.cafe_links_action_menu_view);
    final String photo = cafe.getPlace().getPhoto();
    if (SDK_INT >= LOLLIPOP) {
      options = setTransitionNameAndGetOptions(activityFrom
          // share toolbar & stub view to avoid overlaying
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar_background)
              , R.string.toolbar_background_transition_name)
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar)
              , R.string.toolbar_transition_name)
          , photo == null ? null : new SharedElementHolder(photoScrimLayout
              , R.string.cafe_photo_scrim_layout_transition_name)
          , new SharedElementHolder(photoImageView
              , R.string.cafe_photo_image_view_transition_name)
          , new SharedElementHolder(nameTextView
              , R.string.cafe_name_text_view_transition_name)
          , new SharedElementHolder(linksActionMenuView
              , R.string.cafe_links_action_menu_view_transition_name))
          .toBundle();

      activityFrom.getWindow().setExitTransition(new Fade());
    }

    final Intent intent = new Intent(activityFrom, CafeActivity.class);
    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(ENTER_CAFE_PLACE_ID_EXTRA, cafe.getPlace().getId());
    putDrawableToCache(TRANSITION_CACHED_DRAWABLE_KEY,
        photo == null ? null : photoImageView.getDrawable());


    startActivity(activityFrom, intent, options);
  }

  public static void launchMainActivity(@NonNull Activity activityFrom, @Nullable Long scrollToCafePlaceId) {
    Bundle options = null;
    if (SDK_INT >= LOLLIPOP) {
      options = setTransitionNameAndGetOptions(activityFrom
          // share toolbar & stub view to avoid overlaying
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar_background)
          , R.string.toolbar_background_transition_name)
          , new SharedElementHolder(activityFrom.findViewById(R.id.toolbar)
          , R.string.toolbar_transition_name))
          .toBundle();

      activityFrom.getWindow().setExitTransition(new Slide(Gravity.START));
    }

    final Intent intent = new Intent(activityFrom, MainActivity.class);
    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(SCROLL_TO_CAFE_PLACE_ID_EXTRA, scrollToCafePlaceId);

    startActivity(activityFrom, intent, options);
  }

  public static void launchByUrl(@NonNull Context context, @NonNull String url) {
    final Uri uri = Uri.parse(url);
    final Intent intent = new Intent(ACTION_VIEW, uri);

    context.startActivity(intent);
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
        if (sharedElementHolder != null && sharedElementHolder.view != null) {
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
