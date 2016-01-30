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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.gui.helpers.NavigateHelper;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.kuelye.notbadcoffee.Application.getBus;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchCafeActivity;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchMapActivity;

public class CafesFragment extends AbstractBaseFragment implements CafesAdapter.Callback {

  private RecyclerView mRecyclerView;
  private LinearLayoutManager mLayoutManager;
  private CafesAdapter mCafesAdapter;

  @Override
  @NonNull protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container) {
    return inflater.inflate(R.layout.cafes_fragment, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (SDK_INT >= LOLLIPOP) {
      final Transition exitTransition = new Fade();
      getActivity().getWindow().setExitTransition(exitTransition);
    }
  }

  @Override
  @NonNull public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container, savedInstanceState);

    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    mLayoutManager = new LinearLayoutManager(getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.addItemDecoration(new CafesAdapter.HeaderDecoration(getActivity()));
    mCafesAdapter = new CafesAdapter(getActivity(), this);
    mRecyclerView.setAdapter(mCafesAdapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    getBus().register(this);
    update();
  }

  @Override
  public void onPause() {
    super.onPause();

    getBus().unregister(this);
  }

  @Override
  public void onLocationClicked(
      @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    launchMapActivity(getActivity(), cafeRowViewHolder, cafe);
  }

  @Override
  public void onPhotoClicked(
      @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    launchCafeActivity(getActivity(), cafeRowViewHolder.photoImageView, cafe.getPlace().getId());
  }

  @Subscribe
  public void onGetCafesEventGotten(GetCafesAsyncTask.Event getCafesEvent) {
    final Cafes cafes = getCafesEvent.getCafes();
    if (cafes != null) {
      mCafesAdapter.set(cafes);
    }
  }

  /* =========================== HIDDEN ============================= */

  private void update() {
    new GetCafesAsyncTask().execute();
  }

}
