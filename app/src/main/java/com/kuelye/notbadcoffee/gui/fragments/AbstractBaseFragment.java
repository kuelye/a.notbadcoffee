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

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kuelye.notbadcoffee.R;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

public abstract class AbstractBaseFragment extends Fragment
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  protected static final long ANIMATION_DURATION_DEFAULT = 200;
  protected static final long ANIMATION_DELAY_DEFAULT = 100;

  protected Toolbar mToolbar;

  private boolean mOnViewCreatedCalled = false;

  private GoogleApiClient mGoogleApiClient;
  protected Location mLastLocation;

  @NonNull protected abstract View inflateView(LayoutInflater inflater, @Nullable ViewGroup container);

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    final View view = inflateView(inflater, container);

    mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    mToolbar.setTitle(R.string.application_name);

    // translucent status bar bug fix (it height didn't included, so it is considered as 0dp)
    final View stubView = view.findViewById(R.id.stub_view);
    stubView.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);

    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();
    }



    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (!mOnViewCreatedCalled) {
      mOnViewCreatedCalled = true;

      onBeforeViewShowed();

      if (SDK_INT >= LOLLIPOP) {
        getActivity().getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

          @Override
          public void onTransitionStart(Transition transition) {
            AbstractBaseFragment.this.onEnterTransitionStart();
          }

          @Override
          public void onTransitionEnd(Transition transition) {
            AbstractBaseFragment.this.onEnterTransitionEnd();
          }

          @Override
          public void onTransitionCancel(Transition transition) {
            // stub
          }

          @Override
          public void onTransitionPause(Transition transition) {
            // stub
          }

          @Override
          public void onTransitionResume(Transition transition) {
            // stub
          }

        });
      }
    }

    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onResume() {
    mGoogleApiClient.connect();
    super.onResume();
  }

  @Override
  public void onPause() {
    mGoogleApiClient.disconnect();
    super.onPause();
  }

  @Override
  public void onConnected(@Nullable Bundle connectionHint) {
    mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
  }

  @Override
  public void onConnectionSuspended(int cause) {
    // TODO
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult result) {
    // TODO
  }

  public boolean onBackPressed() {
    return true;
  }

  protected void onEnterTransitionStart() {
    // stub
  }

  protected void onEnterTransitionEnd() {
    // stub
  }

  protected void finish() {
    if (getActivity() != null) {
      getActivity().finishAfterTransition();
    }
  }

  protected void onBeforeViewShowed() {
    // stub
  }

}