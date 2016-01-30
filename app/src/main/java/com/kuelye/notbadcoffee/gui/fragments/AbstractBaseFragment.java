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
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuelye.notbadcoffee.R;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

public abstract class AbstractBaseFragment extends Fragment {

  protected Toolbar mToolbar;

  private boolean mOnViewCreatedCalled = false;

  @NonNull protected abstract View inflateView(LayoutInflater inflater, @Nullable ViewGroup container);

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    final View view = inflateView(inflater, container);

    if (savedInstanceState == null) {
      // translucent status bar bug fix (it height didn't included, so it is considered as 0dp)
      final View stubView = view.findViewById(R.id.stub_view);
      stubView.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);

      mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
      mToolbar.setTitle(R.string.application_name);
    }

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (!mOnViewCreatedCalled) {
      mOnViewCreatedCalled = true;

      onBeforeViewShowed();

      if (savedInstanceState == null && SDK_INT >= LOLLIPOP) {
        getActivity().getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

          @Override
          public void onTransitionStart(Transition transition) {
            AbstractBaseFragment.this.onTransitionStart();
          }

          @Override
          public void onTransitionEnd(Transition transition) {
            AbstractBaseFragment.this.onTransitionEnd();
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

  protected void onTransitionStart() {
    // stub
  }

  protected void onTransitionEnd() {
    // stub
  }

  protected void onBeforeViewShowed() {
    // stub
  }

}
