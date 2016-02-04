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
import com.google.android.gms.maps.model.CameraPosition;
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

  // used to fix MapView onCreate() crash when orientation changes
  private static final String MAP_VIEW_SAVED_STATE_KEY = "MAP_VIEW_SAVED_STATE";

  @Bind(R.id.map_view) protected MapView mMapView;
  @Nullable protected GoogleMap mGoogleMap;

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    final Bundle mapViewSavedInstanceState
        = savedInstanceState == null
        ? null
        : savedInstanceState.getBundle(MAP_VIEW_SAVED_STATE_KEY);
    mMapView.onCreate(mapViewSavedInstanceState);
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
    final Bundle mapViewOutState = new Bundle(outState);
    mMapView.onSaveInstanceState(mapViewOutState);
    outState.putBundle(MAP_VIEW_SAVED_STATE_KEY, mapViewOutState);

    super.onSaveInstanceState(outState);
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

  protected void centerCamera(final boolean animate, Marker... markers) {
    if (mGoogleMap != null) {
      final LatLngBounds.Builder builder = new LatLngBounds.Builder();
      for (Marker marker : markers) {
        builder.include(marker.getPosition());
      }
      if (mLastLocation != null) {
        builder.include(locationToLatLng(mLastLocation));
      }
      final LatLngBounds bounds = builder.build();

      // workaround for bug with newLatLngBounds() crash when dimensions of the map view
      // haven't been determined yet (see http://stackoverflow.com/questions/13692579)
      if (mMapView.getWidth() == 0) {
        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
          @Override
          public void onCameraChange(CameraPosition cameraPosition) {
            postCenterCamera(bounds, animate);
            mGoogleMap.setOnCameraChangeListener(null);
          }
        });
      } else {
        postCenterCamera(bounds, animate);
      }
    }
  }

  protected void fillMap() {
    // stub
  }

  /* ================================================================ */

  private void postCenterCamera(@NonNull LatLngBounds bounds, boolean animate) {
    if (mGoogleMap != null) {
      final CameraUpdate cameraUpdate = newLatLngBounds(bounds, MAP_CENTER_PADDING);
      if (animate) {
        mGoogleMap.animateCamera(cameraUpdate);
      } else {
        mGoogleMap.moveCamera(cameraUpdate);
      }
    }
  }

}
