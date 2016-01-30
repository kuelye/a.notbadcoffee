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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuelye.notbadcoffee.Application;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.helpers.NavigateHelper;
import com.kuelye.notbadcoffee.logic.tasks.GetCafeAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Place;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.support.v4.app.ActivityCompat.postponeEnterTransition;
import static android.support.v4.app.ActivityCompat.startPostponedEnterTransition;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.TRANSITION_CACHED_BITMAP_KEY;

public class CafeFragment extends AbstractCafeFragment {

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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //postponeEnterTransition(getActivity());
  }

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    if (savedInstanceState == null) {
      mToolbar.setTitle(null);
      mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
      mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          getActivity().supportFinishAfterTransition();
        }
      });
    }

    return view;
  }

  @Override
  protected void onBeforeViewShowed() {
    super.onBeforeViewShowed();

    final View view = getView();
    if (view != null && SDK_INT >= LOLLIPOP) {
      final View photoView = view.findViewById(R.id.cafe_photo_image_view);
      photoView.setTransitionName(getString(R.string.cafe_photo_image_view_transition_name));
    }
  }

  @Override
  protected void onTransitionStart() {
    final View view = getView();
    if (view != null) {
      mMapView.setAlpha(0);

      final View toolbarBackgroundView = view.findViewById(R.id.toolbar_background);
      toolbarBackgroundView.animate()
          .alpha(0)
          .setDuration(300);
    }
  }

  @Override
  protected void onTransitionEnd() {
    final View view = getView();
    if (view != null) {
      mToolbar.setAlpha(0);
      mMapView.setTranslationY(-mMapView.getHeight());

      mToolbar.animate()
          .alpha(1)
          .setDuration(300)
          .setStartDelay(100);
      mMapView.animate()
          .alpha(1)
          .translationY(0)
          .setDuration(300)
          .setStartDelay(200);
    }
  }

  @Subscribe
  public void onGetCafeEventGotten(GetCafeAsyncTask.Event getCafeEvent) {
    final Cafe cafe = getCafeEvent.getCafe();
    final View view = getView();
    if (cafe != null && view != null) {
      final Place place = cafe.getPlace();

      final ImageView photoImageView = (ImageView) view.findViewById(R.id.cafe_photo_image_view);
      final Bitmap cachedPhotoBitmap = Application.getBitmapFromCache(TRANSITION_CACHED_BITMAP_KEY);
      Picasso.with(getActivity())
          .load(place.getPhoto())
          .placeholder(new BitmapDrawable(getResources(), cachedPhotoBitmap))
          .fit()
          .into(photoImageView, new Callback() {

            @Override
            public void onSuccess() {
              //startPostponedEnterTransition(getActivity());
            }

            @Override
            public void onError() {
              //startPostponedEnterTransition(getActivity());
            }

          });
    }
  }

}
