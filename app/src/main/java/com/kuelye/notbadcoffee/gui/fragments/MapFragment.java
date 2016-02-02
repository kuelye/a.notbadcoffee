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

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kuelye.notbadcoffee.Application;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static butterknife.ButterKnife.bind;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;
import static com.kuelye.notbadcoffee.Application.getLastLocation;
import static com.kuelye.notbadcoffee.Application.popBitmapFromCache;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillLocationLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillPhotoLayout;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.TRANSITION_CACHED_BITMAP_KEY;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchCafeActivity;

public class MapFragment extends AbstractCafeFragment implements OnMapReadyCallback {

  @Bind(R.id.card_view) protected CardView mCardView;
  @Bind(R.id.cafe_photo_image_view) protected ImageView mPhotoImageView;
  @Bind(R.id.cafe_photo_clickable_image_view) protected ImageView mPhotoClickableImageView;
  @Bind(R.id.cafe_name_text_view) protected TextView mNameTextView;
  @Bind(R.id.cafe_place_layout) protected ViewGroup mPlaceLayout;

  @Nullable private Cafes mCafes;

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

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    bind(this, view);

    return view;
  }

  @Override
  protected void onBeforeViewShowed() {
    super.onBeforeViewShowed();

    if (SDK_INT >= LOLLIPOP) {
      mCardView.setTransitionName(getString(R.string.card_view_transition_name));
    }
  }

  @Override
  @Subscribe
  public void onCafeGotten(GetCafeAsyncTask.Event getCafeEvent) {
    super.onCafeGotten(getCafeEvent);

    if (mCafe != null) {
      final Bitmap cachedPhotoBitmap = popBitmapFromCache(TRANSITION_CACHED_BITMAP_KEY);
      final Drawable cachedPhoto = new BitmapDrawable(getResources(), cachedPhotoBitmap);
      fillPhotoLayout(getActivity(), mPhotoImageView, mNameTextView, mCafe, cachedPhoto);
      fillLocationLayout(getActivity(), mPlaceLayout, mCafe, getLastLocation());

      mPhotoClickableImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          launchCafeActivity(getActivity(), mPhotoImageView, mNameTextView, getCafePlaceId());
        }
      });
      mPlaceLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mGoogleMap != null) {
            final LatLng placeLatLng = mCafe.getPlace().getLocation().toLatLng();
            final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(placeLatLng);
            mGoogleMap.animateCamera(cameraUpdate);
          }
        }
      });
    }
  }

  @Override
  protected void update() {
    super.update();

    new GetCafesAsyncTask().execute();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    super.onMapReady(googleMap);

    googleMap.setPadding(0, mToolbar.getHeight() + getStatusBarHeight(getActivity())
        , 0, mCardView.getHeight());
  }

  @Override
  protected void fillMap() {
    if (mGoogleMap != null && mCafes != null) {
      final List<Marker> markers = new ArrayList<>();
      Marker selectedMarker = null;
      for (Cafe cafe : mCafes) {
        final Marker marker = mGoogleMap.addMarker(new MarkerOptions()
            .position(cafe.getPlace().getLocation().toLatLng()));
        marker.setTitle(cafe.getName());
        marker.setSnippet(cafe.getPlace().getAddress());

        if (cafe == mCafe) {
          selectedMarker = marker;
        }
        markers.add(marker);
      }

      centerCamera(false, markers.toArray(new Marker[markers.size()]));
      if (selectedMarker != null) {
        selectedMarker.showInfoWindow();
      }
    }
  }

  @Subscribe
  public void onCafesGotten(GetCafesAsyncTask.Event event) {
    mCafes = event.getCafes();
  }

  @Subscribe
  public void onLocationGotten(OnLocationGottenEvent event) {
    if (mCafe != null) {
      fillLocationLayout(getActivity(), mPlaceLayout, mCafe, event.getLocation());
    }
  }

}
