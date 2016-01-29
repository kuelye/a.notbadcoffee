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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Place;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import static com.kuelye.notbadcoffee.Application.getBus;

public class MapFragment extends Fragment implements OnMapReadyCallback {

  public static final String CAFE_PLACE_ID_EXTRA = "CAFE_PLACE_ID";

  private MapView mMapView;

  public static MapFragment newInstance(int cafePlaceId) {
    final MapFragment mapFragment = new MapFragment();

    final Bundle arguments = new Bundle();
    arguments.putInt(CAFE_PLACE_ID_EXTRA, cafePlaceId);
    mapFragment.setArguments(arguments);

    return mapFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.map_fragment, container, false);

    mMapView = (MapView) view.findViewById(R.id.mapview);
    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(this);

    final Intent intent = getActivity().getIntent();


    return view;
  }

  public void onResume() {
    super.onResume();
    mMapView.onResume();

    getBus().register(this);
    update();
  }

  public void onPause() {
    mMapView.onPause();
    super.onPause();

    getBus().unregister(this);
  }

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mMapView.onSaveInstanceState(outState);
  }
  public void onLowMemory() {
    mMapView.onLowMemory();
    super.onLowMemory();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    googleMap.setMyLocationEnabled(true);
  }

  @Subscribe
  public void onGetCafeEvent(GetCafeAsyncTask.Event getCafeEvent) {
    final Cafe cafe = getCafeEvent.getCafe();
    final View view = getView();
    if (cafe != null && view != null) {
      final Place place = cafe.getPlace();

      final ImageView photoImageView = (ImageView) view.findViewById(R.id.cafe_row_photo_imageview);
      Picasso.with(getActivity())
          .load(place.getPhoto())
          .fit()
          .into(photoImageView);
      final TextView addressTextView = (TextView) view.findViewById(R.id.cafe_row_location_address_textview);
      addressTextView.setText(place.getAddress());
      final TextView metroTextView = (TextView) view.findViewById(R.id.cafe_row_location_metro_textview);
      metroTextView.setText(place.getMetro());
    }
  }

  /* =========================== HIDDEN ============================= */

  private void update() {
    new GetCafeAsyncTask().execute(getActivity().getIntent().getIntExtra(CAFE_PLACE_ID_EXTRA, -1));
  }

}
