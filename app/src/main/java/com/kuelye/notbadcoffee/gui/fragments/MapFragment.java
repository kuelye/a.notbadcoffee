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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.kuelye.notbadcoffee.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

  private MapView mMapView;

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

    return view;
  }

  public void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  public void onPause() {
    mMapView.onPause();
    super.onPause();
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

}