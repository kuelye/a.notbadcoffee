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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Menu;
import com.kuelye.notbadcoffee.model.MenuRow;
import com.kuelye.notbadcoffee.model.Place;
import com.kuelye.notbadcoffee.model.Timetable;
import com.kuelye.notbadcoffee.model.TimetableRow;
import com.squareup.picasso.Picasso;

import static android.view.View.inflate;

public final class CafeHelper {

  public static void fillPhotoLayout(
      @NonNull Context context
      , @NonNull ImageView cafePhotoImageView
      , @NonNull TextView cafeNameTextView
      , @NonNull Cafe cafe
      , @Nullable Drawable cafeCachedPhoto) {
    Picasso.with(context)
        .load(cafe.getPlace().getPhoto())
        .placeholder(cafeCachedPhoto)
        .fit()
        .centerCrop()
        .into(cafePhotoImageView);
    cafeNameTextView.setText(cafe.getName());
  }

  public static void fillLocationLayout(
      @NonNull TextView cafePlaceAddressTextView
      , @NonNull TextView cafePlaceMetroTextView
      , @NonNull Cafe cafe) {
    final Place place = cafe.getPlaces().get(0);
    cafePlaceAddressTextView.setText(place.getAddress());
    cafePlaceMetroTextView.setText(place.getMetro());
  }

  public static void fillMenuLayout(@NonNull Context context
      , @NonNull ViewGroup cafeMenuLayout
      , @NonNull Cafe cafe) {
    final Menu menu = cafe.getMenu();
    if (menu == null) {
      cafeMenuLayout.setVisibility(View.GONE);
    } else {
      final ViewGroup cafeMenuContentLayout
          = (ViewGroup) cafeMenuLayout.findViewById(R.id.cafe_menu_content_layout);
      cafeMenuContentLayout.removeAllViews();
      for (MenuRow menuRow : menu) {
        final View menuRowView = inflate(context, R.layout.cafe_menu_row, null);
        ((TextView) menuRowView.findViewById(R.id.cafe_menu_item_text_view))
            .setText(menuRow.getItem());
        ((TextView) menuRowView.findViewById(R.id.cafe_menu_cost_text_view))
            .setText(menuRow.getCost());

        cafeMenuContentLayout.addView(menuRowView);
      }

      cafeMenuLayout.setVisibility(View.VISIBLE);
    }
  }

  public static void fillTimetableLayout(@NonNull Context context
      , @NonNull ViewGroup cafeTimetableLayout
      , @NonNull Cafe cafe) {
    final Timetable timetable = cafe.getTimetable();
    if (timetable == null) {
      cafeTimetableLayout.setVisibility(View.GONE);
    } else {
      final ViewGroup cafeTimetableContentLayout
          = (ViewGroup) cafeTimetableLayout.findViewById(R.id.cafe_timetable_content_layout);
      cafeTimetableContentLayout.removeAllViews();
      for (TimetableRow timetableRow : timetable) {
        final View timetableRowView = inflate(
            context, R.layout.cafe_timetable_row, null);
        ((TextView) timetableRowView.findViewById(R.id.cafe_timetable_day_range_text_view))
            .setText(timetableRow.getDayRange().getDisplayString(context));
        ((TextView) timetableRowView.findViewById(R.id.cafe_timetable_time_range_text_view))
            .setText(timetableRow.getTimeRange().getDisplayString());

        cafeTimetableContentLayout.addView(timetableRowView);
      }

      cafeTimetableLayout.setVisibility(View.VISIBLE);
    }
  }

}
