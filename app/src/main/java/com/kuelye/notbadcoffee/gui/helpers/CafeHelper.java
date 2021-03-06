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
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kuelye.components.utils.AndroidUtils;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Link;
import com.kuelye.notbadcoffee.model.Menu;
import com.kuelye.notbadcoffee.model.MenuRow;
import com.kuelye.notbadcoffee.model.Place;
import com.kuelye.notbadcoffee.model.Timetable;
import com.kuelye.notbadcoffee.model.TimetableRow;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.Menu.FIRST;
import static android.view.Menu.NONE;
import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;
import static com.kuelye.components.utils.AndroidUtils.getWidthInPxs;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchByUrl;

public final class CafeHelper {

  private static final int METRES_IN_KILOMETER = 1000;

  public static void fillHeaderLayout(
      final @NonNull Context context
      , @NonNull ViewGroup cafeHeaderLayout
      , @Nullable Cafe cafe
      , @Nullable Drawable cafeCachedPhoto) {
    final ImageView photoImageView = (ImageView) cafeHeaderLayout.findViewById(R.id.cafe_photo_image_view);
    final ProgressBar photoProgressBar = (ProgressBar) cafeHeaderLayout.findViewById(R.id.cafe_photo_progress_bar);
    final ViewGroup scrimLayout = (ViewGroup) cafeHeaderLayout.findViewById(R.id.cafe_photo_scrim_layout);
    if (cafe == null || cafe.getPlace().getPhoto() == null) {
      photoProgressBar.setVisibility(INVISIBLE);
      scrimLayout.setVisibility(INVISIBLE);
      photoImageView.setImageResource(R.drawable.photo_placeholder);
    } else {
      if (cafeCachedPhoto == null) {
        photoProgressBar.setVisibility(VISIBLE);
        scrimLayout.setVisibility(INVISIBLE);
        final int width = getWidthInPxs(context);
        final int height = (int) (width
            / context.getResources().getFraction(R.fraction.cafe_photo_image_view_aspect_ratio, 1, 1));
        Picasso.with(context)
            .load(cafe.getPlace().getPhoto())
            .resize(width, height)
            .centerCrop()
            .placeholder(R.drawable.photo_placeholder)
            .into(photoImageView, new Callback() {

              @Override
              public void onSuccess() {
                photoProgressBar.setVisibility(INVISIBLE);
                scrimLayout.setVisibility(VISIBLE);
                photoImageView.setTag(R.id.cafe_photo_image_view_loaded_tag, true);
              }

              @Override
              public void onError() {
                photoProgressBar.setVisibility(INVISIBLE);
                scrimLayout.setVisibility(INVISIBLE);
                photoImageView.setTag(R.id.cafe_photo_image_view_loaded_tag, false);
              }

            });
      } else {
        photoProgressBar.setVisibility(INVISIBLE);
        scrimLayout.setVisibility(VISIBLE);
        photoImageView.setImageDrawable(cafeCachedPhoto);
        photoImageView.setTag(R.id.cafe_photo_image_view_loaded_tag, true);
      }
    }

    final TextView nameTextView = (TextView) cafeHeaderLayout.findViewById(R.id.cafe_name_text_view);
    final ActionMenuView linksActionMenuView = (ActionMenuView) cafeHeaderLayout.findViewById(R.id.cafe_links_action_menu_view);
    final android.view.Menu menu = linksActionMenuView.getMenu();
    menu.removeGroup(FIRST);
    if (cafe != null) {
      nameTextView.setText(cafe.getName());
      final List<Link> links = cafe.getLinks();
      if (links != null) {
        for (int i = 0; i < links.size(); ++i) {
          final Link link = links.get(i);
          menu.add(FIRST, FIRST + i, NONE, link.getType().getDisplayStringResource())
              .setIcon(link.getType().getIconResource())
              .setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS)
              .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                  launchByUrl(context, link.getUrl());

                  return true;
                }
              });
        }
      }
    }
  }

  public static void fillLocationLayout(@NonNull Context context
      , @NonNull ViewGroup cafeLocationLayout
      , @NonNull Cafe cafe
      , @Nullable Location location) {
    final Place place = cafe.getPlaces().get(0);
    final TextView placeAddressTextView
        = (TextView) cafeLocationLayout.findViewById(R.id.cafe_place_address_text_view);
    placeAddressTextView.setText(place.getAddress());
    final TextView placeMetroTextView
        = (TextView) cafeLocationLayout.findViewById(R.id.cafe_place_metro_text_view);
    placeMetroTextView.setText(place.getMetro());
    final TextView placeDistanceTextView
        = (TextView) cafeLocationLayout.findViewById(R.id.cafe_place_distance_text_view);
    if (location != null) {
      double distance = location.distanceTo(cafe.getPlace().getLocation().toLocation());
      String distanceTemplate = context.getString(R.string.cafe_distance_m_template);
      if (distance >= METRES_IN_KILOMETER) {
        distanceTemplate = context.getString(R.string.cafe_distance_km_template);
        distance /= METRES_IN_KILOMETER;
      }
      placeDistanceTextView.setText(String.format(distanceTemplate, distance));
    } else {
      placeDistanceTextView.setText(null);
    }
  }

  public static void fillMenuLayout(@NonNull Context context
      , @NonNull ViewGroup cafeMenuLayout
      , @NonNull Cafe cafe) {
    final Menu menu = cafe.getMenu();
    if (menu == null) {
      cafeMenuLayout.setVisibility(GONE);
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

      cafeMenuLayout.setVisibility(VISIBLE);
    }
  }

  public static void fillTimetableLayout(@NonNull Context context
      , @NonNull ViewGroup cafeTimetableLayout
      , @NonNull Cafe cafe) {
    final Timetable timetable = cafe.getTimetable();
    if (timetable == null) {
      cafeTimetableLayout.setVisibility(GONE);
    } else {
      final ViewGroup cafeTimetableContentLayout
          = (ViewGroup) cafeTimetableLayout.findViewById(R.id.cafe_timetable_content_layout);
      cafeTimetableContentLayout.removeAllViews();
      for (TimetableRow timetableRow : timetable) {
        final View timetableRowView = inflate(
            context, R.layout.cafe_timetable_row, null);
        final TextView dayRangeTextView =
            ((TextView) timetableRowView.findViewById(R.id.cafe_timetable_day_range_text_view));
        dayRangeTextView.setText(timetableRow.getDayRange().getDisplayString(context));
        final TextView timeRangeTextView =
            ((TextView) timetableRowView.findViewById(R.id.cafe_timetable_time_range_text_view));
        timeRangeTextView.setText(timetableRow.getTimeRange().getDisplayString());
        if (timetableRow.getDayRange().isIncludeToday()) {
          dayRangeTextView.setTextAppearance(context, R.style.TextAppearance_AppTheme_Body1);
          timeRangeTextView.setTextAppearance(context, R.style.TextAppearance_AppTheme_Body1);
        } else {
          dayRangeTextView.setTextAppearance(context, R.style.TextAppearance_AppTheme_Caption);
          timeRangeTextView.setTextAppearance(context, R.style.TextAppearance_AppTheme_Caption);
        }

        cafeTimetableContentLayout.addView(timetableRowView);
      }

      cafeTimetableLayout.setVisibility(VISIBLE);
    }
  }

}
