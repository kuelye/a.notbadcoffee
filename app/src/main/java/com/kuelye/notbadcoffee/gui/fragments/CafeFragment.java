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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.views.MapView;
import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static butterknife.ButterKnife.bind;
import static com.kuelye.notbadcoffee.Application.getDrawableFromCache;
import static com.kuelye.notbadcoffee.Application.getLastLocation;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillLocationLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillMenuLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillHeaderLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillTimetableLayout;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.TRANSITION_CACHED_DRAWABLE_KEY;

public class CafeFragment extends AbstractCafeFragment {

  @Bind(R.id.scroll_view) protected ScrollView mScrollView;
  @Bind(R.id.cafe_header_layout) protected ViewGroup mHeaderLayout;
  @Bind(R.id.cafe_name_and_links_layout) protected ViewGroup mNameAndLinksLayout;
  @Bind(R.id.cafe_photo_image_view) protected ImageView mPhotoImageView;
  @Bind(R.id.cafe_info_layout) protected ViewGroup mInfoLayout;
  @Bind(R.id.cafe_place_layout) protected ViewGroup mPlaceLayout;
  @Bind(R.id.cafe_more_info_header_layout) protected ViewGroup mMoreInfoHeaderLayout;
  @Bind(R.id.cafe_more_info_layout) protected ViewGroup mMoreInfoLayout;
  @Bind(R.id.cafe_menu_layout) protected ViewGroup mMenuLayout;
  @Bind(R.id.cafe_timetable_layout) protected ViewGroup mTimetableLayout;

  public static CafeFragment newInstance(int cafePlaceId) {
    final CafeFragment cafeFragment = new CafeFragment();

    final Bundle arguments = new Bundle();
    putToBundle(arguments, cafePlaceId);
    cafeFragment.setArguments(arguments);

    return cafeFragment;
  }

  @Override
  @NonNull protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container) {
    return inflater.inflate(R.layout.cafe_fragment, container, false);
  }

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    bind(this, view);

    if (savedInstanceState == null) {
      mToolbar.setTitle(null);
      mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          getActivity().onBackPressed();
        }
      });

      mMoreInfoHeaderLayout.setVisibility(View.GONE);
      mMoreInfoLayout.setVisibility(View.VISIBLE);
      mInfoLayout.setAlpha(0);
      mMapView.setAlpha(0);

      // fix to avoid ScrollView stealing of MapView touch events
      mMapView.setOnInterceptTouchListener(new MapView.OnInterceptTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              mScrollView.requestDisallowInterceptTouchEvent(true);
              break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
              mScrollView.requestDisallowInterceptTouchEvent(false);
              break;
          }

          return false;
        }
      });
    }

    return view;
  }

  @Override
  protected void onBeforeViewShowed() {
    super.onBeforeViewShowed();

    if (SDK_INT >= LOLLIPOP) {
      mPhotoImageView.setTransitionName(getString(R.string.cafe_photo_image_view_transition_name));
      mNameAndLinksLayout.setTransitionName(getString(R.string.cafe_name_and_links_layout_transition_name));
    }
  }

  @Override
  protected void onEnterTransitionEnd() {
    super.onEnterTransitionEnd();

    mToolbar.setAlpha(0);
    mMapView.setTranslationY(-mMapView.getHeight());
    mInfoLayout.setTranslationY(-mInfoLayout.getHeight());

    mToolbar.animate()
        .alpha(1)
        .setDuration(ANIMATION_DURATION_DEFAULT)
        .setStartDelay(ANIMATION_DELAY_DEFAULT);
    mMapView.animate()
        .alpha(1)
        .translationY(0)
        .setDuration(ANIMATION_DURATION_DEFAULT)
        .setStartDelay(2 * ANIMATION_DELAY_DEFAULT);
    mInfoLayout.animate()
        .alpha(1)
        .translationY(0)
        .setDuration(ANIMATION_DURATION_DEFAULT)
        .setStartDelay(3 * ANIMATION_DELAY_DEFAULT);
  }

  @Override
  @Subscribe
  public void onCafeGotten(GetCafeAsyncTask.Event getCafeEvent) {
    super.onCafeGotten(getCafeEvent);

    if (mCafe != null) {
      final Drawable cachedPhoto = getDrawableFromCache(TRANSITION_CACHED_DRAWABLE_KEY);
      fillHeaderLayout(getActivity(), mHeaderLayout, mCafe, cachedPhoto);
      fillLocationLayout(getActivity(), mPlaceLayout, mCafe, getLastLocation());
      fillMenuLayout(getActivity(), mMenuLayout, mCafe);
      fillTimetableLayout(getActivity(), mTimetableLayout, mCafe);

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
  protected void fillMap() {
    if (mGoogleMap != null && mCafe != null) {
      final Marker marker = mGoogleMap.addMarker(new MarkerOptions()
          .position(mCafe.getPlace().getLocation().toLatLng()));

      centerCamera(false, marker);
    }
  }

  @Subscribe
  public void onLocationGotten(OnLocationGottenEvent event) {
    if (mCafe != null) {
      fillLocationLayout(getActivity(), mPlaceLayout, mCafe, event.getLocation());
    }
  }

}
