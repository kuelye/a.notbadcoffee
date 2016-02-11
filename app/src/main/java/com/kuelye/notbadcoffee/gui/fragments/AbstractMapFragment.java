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

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds;
import static com.kuelye.components.utils.AndroidUtils.locationToLatLng;

public abstract class AbstractMapFragment extends AbstractBaseFragment implements OnMapReadyCallback {

  private static final int MAP_CENTER_PADDING = 32;

  // used to fix MapView onCreate() crash when orientation changes
  private static final String MAP_VIEW_SAVED_STATE_KEY = "MAP_VIEW_SAVED_STATE";
  private static final String CAMERA_SHOULD_BE_CENTERED_KEY = "CAMERA_SHOULD_BE_CENTERED";

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
    if (mGoogleMap != null) {
      try {
        mGoogleMap.setMyLocationEnabled(true);
      } catch (SecurityException e) {
        // ignore, location won't used
      }
    }
  }

  @Override
  public void onPause() {
    super.onPause();

    mMapView.onPause();
    if (mGoogleMap != null) {
      try {
        mGoogleMap.setMyLocationEnabled(false);
      } catch (SecurityException e) {
        // ignore, location wasn't enabled
      }
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    final Bundle mapViewOutState = new Bundle(outState);
    mMapView.onSaveInstanceState(mapViewOutState);
    outState.putBundle(MAP_VIEW_SAVED_STATE_KEY, mapViewOutState);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();

    mMapView.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    mMapView.onDestroy();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mGoogleMap = googleMap;
    try {
      googleMap.setMyLocationEnabled(true);
    } catch (SecurityException e) {
      // ignore, location won't used
    }

    fillMap();
  }

  @Override
  public void onConnected(@Nullable Bundle connectionHint) {
    super.onConnected(connectionHint);

    fillMap();
  }

  /**
   * @return Returns true if camera was centered successfully.
   */
  protected boolean centerCamera(final boolean animate, Marker... markers) {
    boolean result = false;
    if (mGoogleMap != null) {
      final LatLngBounds.Builder builder = new LatLngBounds.Builder();
      for (Marker marker : markers) {
        builder.include(marker.getPosition());
      }
      if (mLastLocation != null) {
        result = true;
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

    return result;
  }

  protected void fillMap() {
    // stub
  }

  protected boolean getCameraShouldBeCentered() {
    return getArguments().getBoolean(CAMERA_SHOULD_BE_CENTERED_KEY, true);
  }

  protected void setCameraShouldBeCentered(boolean cameraShouldBeCentered) {
    getArguments().putBoolean(CAMERA_SHOULD_BE_CENTERED_KEY, cameraShouldBeCentered);
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
