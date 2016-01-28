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

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.activities.MapActivity;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.gui.fragments.MapFragment;
import com.kuelye.notbadcoffee.model.Cafe;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static android.support.v4.view.ViewCompat.setTransitionName;
import static com.kuelye.notbadcoffee.gui.fragments.MapFragment.CAFE_PHOTO_EXTRA;
import static com.kuelye.notbadcoffee.gui.fragments.MapFragment.CAFE_PLACE_ADDRESS_EXTRA;
import static com.kuelye.notbadcoffee.gui.fragments.MapFragment.CAFE_PLACE_METRO_EXTRA;

public final class NavigateHelper {

  public static void launchMapActivity(
      @NonNull Activity sourceActivity
      , @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    final ActivityOptionsCompat options = setTransitionNameAndGetOptions(sourceActivity
        , new SharedElementHolder(cafeRowViewHolder.rootView
            , R.string.cafe_row_card_view_transition_name)
        , new SharedElementHolder(cafeRowViewHolder.locationAddressTextView
            , R.string.cafe_row_location_address_text_view_transition_name)
        , new SharedElementHolder(cafeRowViewHolder.locationMetroTextView
            , R.string.cafe_row_location_metro_text_view_transition_name));
    final Intent intent = new Intent(sourceActivity, MapActivity.class);

    intent.putExtra(CAFE_PHOTO_EXTRA, cafe.getPlaces().get(0).getPhoto());
    intent.putExtra(CAFE_PLACE_ADDRESS_EXTRA, cafe.getPlaces().get(0).getAddress());
    intent.putExtra(CAFE_PLACE_METRO_EXTRA, cafe.getPlaces().get(0).getMetro());

    ActivityCompat.startActivity(sourceActivity, intent, options.toBundle());
  }

  /* ========================= HIDDEN =============================== */

  @SuppressWarnings("unchecked")
  private static ActivityOptionsCompat setTransitionNameAndGetOptions(
      @NonNull Activity sourceActivity
      , SharedElementHolder... sharedElementHolders) {
    List<Pair<View, String>> sharedElements = new ArrayList<>();
    if (sharedElementHolders != null) {
      for (SharedElementHolder sharedElementHolder : sharedElementHolders) {
        final String transitionName = sourceActivity.getString(sharedElementHolder.transitionNameResource);
        setTransitionName(sharedElementHolder.view, transitionName);
        sharedElements.add(Pair.create(sharedElementHolder.view, transitionName));
      }
    }

    return makeSceneTransitionAnimation(sourceActivity
        , sharedElements.toArray(new Pair[sharedElements.size()]));
  }

  /* ========================= INNER ================================ */

  private static class SharedElementHolder {

    @NonNull public final View view;
    @StringRes public final int transitionNameResource;

    public SharedElementHolder(@NonNull View view, @StringRes int transitionNameResource) {
      this.view = view;
      this.transitionNameResource = transitionNameResource;
    }

  }

}
