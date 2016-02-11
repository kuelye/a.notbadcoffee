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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static butterknife.ButterKnife.bind;
import static com.kuelye.notbadcoffee.Application.getDrawableFromCache;
import static com.kuelye.notbadcoffee.Application.getLastLocation;
import static com.kuelye.notbadcoffee.gui.fragments.CafesFragment.SCROLL_TO_CAFE_PLACE_ID_EXTRA;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillHeaderLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillLocationLayout;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.TRANSITION_CACHED_DRAWABLE_KEY;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchCafeActivity;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchMainActivity;
import static com.kuelye.notbadcoffee.model.Place.STUB_ID;

public class MapFragment extends AbstractCafeFragment implements OnMapReadyCallback {

  private static final String SELECTED_CAFE_PLACE_ID_EXTRA = "SELECTED_CAFE_PLACE_ID";

  @Bind(R.id.card_view) protected CardView mCardView;
  @Bind(R.id.cafe_header_layout) protected ViewGroup mHeaderLayout;
  @Bind(R.id.cafe_photo_clickable_image_view) protected ImageView mPhotoClickableImageView;
  @Bind(R.id.cafe_place_layout) protected ViewGroup mPlaceLayout;

  @Nullable private Cafes mCafes;
  @Nullable private Map<String, Long> mCafeMarkersMap;

  public static MapFragment newInstance(long cafePlaceId) {
    final MapFragment fragment = new MapFragment();

    final Bundle arguments = new Bundle();
    fillArguments(arguments, cafePlaceId);
    arguments.putLong(SELECTED_CAFE_PLACE_ID_EXTRA, cafePlaceId);
    fragment.setArguments(arguments);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (SDK_INT >= LOLLIPOP) {
      getActivity().getWindow().setSharedElementEnterTransition(
          TransitionInflater.from(getActivity()).inflateTransition(R.transition.shared_element_transition_default));
      getActivity().getWindow().setEnterTransition(new Slide(Gravity.END));
    }
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

    mToolbar.inflateMenu(R.menu.map_activity_menu);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.view_cafes_list_action:
            launchMainActivity(getActivity(), getSelectedCafePlaceId());
            return true;
          default:
            return false;
        }
      }
    });

    fillCafeLayout();

    return view;
  }

  @Override
  public boolean onBackPressed() {
    if (getEnterCafePlaceId() != getSelectedCafePlaceId()) {
      final Intent data = new Intent();
      data.putExtra(SCROLL_TO_CAFE_PLACE_ID_EXTRA, getSelectedCafePlaceId());
      getActivity().setResult(RESULT_OK, data);
    }

    return true;
  }

  @Override
  protected void update() {
    new GetCafesAsyncTask(getActivity()).execute(true);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    super.onMapReady(googleMap);

    postUpdateGoogleMapPadding();
  }

  @Override
  protected void fillMap() {
    if (mGoogleMap != null && mCafes != null) {
      mGoogleMap.clear();

      final List<Marker> markers = new ArrayList<>();
      mCafeMarkersMap = new HashMap<>();
      Marker selectedMarker = null;
      for (Cafe cafe : mCafes) {
        final Marker marker = mGoogleMap.addMarker(cafe.getPlace().buildMarkerOptions());
        marker.setTitle(cafe.getName());
        marker.setSnippet(cafe.getPlace().getAddress());

        mCafeMarkersMap.put(marker.getId(), cafe.getPlace().getId());

        if (cafe == mCafe) {
          selectedMarker = marker;
        }
        markers.add(marker);
      }

      mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
          final String markerId = marker.getId();
          if (mCafeMarkersMap.containsKey(markerId)) {
            final long placeId = mCafeMarkersMap.get(markerId);
            setSelectedCafePlaceId(placeId);
            mCafe = mCafes.byPlaceId(placeId);
            fillCafeLayout();
          }

          return false;
        }
      });
      mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
          setSelectedCafePlaceId(STUB_ID);
          mCafe = null;
          fillCafeLayout();
        }
      });

      if (getCameraShouldBeCentered()) {
        final boolean cameraCentered = centerCamera(false, markers.toArray(new Marker[markers.size()]));
        setCameraShouldBeCentered(!cameraCentered);
      }
      if (selectedMarker != null) {
        selectedMarker.showInfoWindow();
      }
    }
  }

  @Override
  protected void onEnterTransitionEnd() {
    super.onEnterTransitionEnd();
  }

  @Subscribe
  public void onCafesGotten(GetCafesAsyncTask.Event event) {
    mCafes = event.getCafes();
    if (mCafes != null) {
      mCafe = mCafes.byPlaceId(getSelectedCafePlaceId());
      fillCafeLayout();
      fillMap();
    }
  }

  @Subscribe
  public void onLocationGotten(OnLocationGottenEvent event) {
    if (mCafe != null) {
      fillLocationLayout(getActivity(), mPlaceLayout, mCafe, event.getLocation());
      fillMap();
    }
  }

  protected long getSelectedCafePlaceId() {
    return getArguments().getLong(SELECTED_CAFE_PLACE_ID_EXTRA, STUB_ID);
  }

  protected void setSelectedCafePlaceId(long cafePlaceId) {
    getArguments().putLong(SELECTED_CAFE_PLACE_ID_EXTRA, cafePlaceId);
  }

  /* ======================== HIDDEN ================================ */

  private void updateGoogleMapPadding() {
    if (mCardView.getHeight() == 0) {
      mCardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
          mCardView.getViewTreeObserver().removeOnPreDrawListener(this);
          postUpdateGoogleMapPadding();

          return true;
        }
      });
    }

    postUpdateGoogleMapPadding();
  }

  private void postUpdateGoogleMapPadding() {
    if (mGoogleMap != null && getActivity() != null) {
      int paddingBottom = 0;
      if (mCardView.getVisibility() != GONE) {
        paddingBottom = mCardView.getHeight();
      }

      mGoogleMap.setPadding(0, mToolbar.getHeight() + getOffsetByStatusBar(getActivity())
          , 0, paddingBottom);
    }
  }

  private void fillCafeLayout() {
    final Drawable cachedPhoto
        = getEnterCafePlaceId() == getSelectedCafePlaceId()
        ? getDrawableFromCache(TRANSITION_CACHED_DRAWABLE_KEY)
        : null;
    fillHeaderLayout(getActivity(), mHeaderLayout, mCafe, cachedPhoto);
    if (getSelectedCafePlaceId() == STUB_ID) {
      mCardView.setVisibility(GONE);
    } else {
      mCardView.setVisibility(VISIBLE);
      if (mCafe != null) {
        fillLocationLayout(getActivity(), mPlaceLayout, mCafe, getLastLocation());
        mCardView.setVisibility(VISIBLE);

        mPhotoClickableImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            launchCafeActivity(getActivity(), mHeaderLayout, mCafe);
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

    updateGoogleMapPadding();
  }

}
