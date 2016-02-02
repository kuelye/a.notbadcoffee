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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.views.MapView;

import butterknife.Bind;

import static butterknife.ButterKnife.bind;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds;
import static com.kuelye.components.utils.AndroidUtils.locationToLatLng;

public abstract class AbstractMapFragment extends AbstractBaseFragment implements OnMapReadyCallback {

  private static final int MAP_CENTER_PADDING = 32;

  @Bind(R.id.map_view) protected MapView mMapView;
  @Nullable protected GoogleMap mGoogleMap;

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(this);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  @Override
  public void onPause() {
    mMapView.onPause();
    super.onPause();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mMapView.onSaveInstanceState(outState);
  }

  @Override
  public void onLowMemory() {
    mMapView.onLowMemory();
    super.onLowMemory();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mGoogleMap = googleMap;
    googleMap.setMyLocationEnabled(true);

    fillMap();
  }

  @Override
  public void onConnected(@Nullable Bundle connectionHint) {
    super.onConnected(connectionHint);

    fillMap();
  }

  protected void centerCamera(boolean animate, Marker... markers) {
    if (mGoogleMap != null) {
      final LatLngBounds.Builder builder = new LatLngBounds.Builder();
      for (Marker marker : markers) {
        builder.include(marker.getPosition());
      }
      if (mLastLocation != null) {
        builder.include(locationToLatLng(mLastLocation));
      }
      final LatLngBounds bounds = builder.build();
      final CameraUpdate cameraUpdate = newLatLngBounds(bounds, MAP_CENTER_PADDING);

      if (animate) {
        mGoogleMap.animateCamera(cameraUpdate);
      } else {
        mGoogleMap.moveCamera(cameraUpdate);
      }
    }
  }

  protected void fillMap() {
    // stub
  }

}
