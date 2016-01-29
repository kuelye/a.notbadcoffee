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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import static com.kuelye.notbadcoffee.Application.getBus;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchMapActivity;

public class CafesFragment extends Fragment implements CafesAdapter.Callback {

  private RecyclerView mRecyclerView;
  private LinearLayoutManager mLayoutManager;
  private CafesAdapter mCafesAdapter;

  @SuppressLint("InflateParams")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.cafes_fragment, null);

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
  public void onLocationLayoutClicked(
      @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    launchMapActivity(getActivity(), cafeRowViewHolder, cafe);
  }

  @Subscribe
  public void onGetCafesEvent(GetCafesAsyncTask.Event getCafesEvent) {
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
