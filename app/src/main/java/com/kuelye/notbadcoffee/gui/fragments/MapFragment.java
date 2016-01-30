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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Place;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchCafeActivity;

public class MapFragment extends AbstractCafeFragment implements OnMapReadyCallback {

  public static MapFragment newInstance(int cafePlaceId) {
    final MapFragment mapFragment = new MapFragment();

    final Bundle arguments = new Bundle();
    putToBundle(arguments, cafePlaceId);
    mapFragment.setArguments(arguments);

    return mapFragment;
  }

  @Override
  @NonNull protected View inflateView(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.map_fragment, container, false);
  }

  @Subscribe
  public void onGetCafeEventGotten(GetCafeAsyncTask.Event getCafeEvent) {
    final Cafe cafe = getCafeEvent.getCafe();
    final View view = getView();
    if (cafe != null && view != null) {
      final Place place = cafe.getPlace();

      final ImageView photoImageView = (ImageView) view.findViewById(R.id.cafe_photo_image_view);
      Picasso.with(getActivity())
          .load(place.getPhoto())
          .fit()
          .into(photoImageView);
      photoImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          launchCafeActivity(getActivity(), view.findViewById(R.id.cafe_photo_layout), getCafePlaceId());
        }
      });
      final TextView addressTextView = (TextView) view.findViewById(R.id.cafe_place_address_text_view);
      addressTextView.setText(place.getAddress());
      final TextView metroTextView = (TextView) view.findViewById(R.id.cafe_place_metro_text_view);
      metroTextView.setText(place.getMetro());
    }
  }

}
