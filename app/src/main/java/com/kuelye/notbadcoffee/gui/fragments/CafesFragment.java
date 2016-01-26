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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuelye.components.concurrent.AbstractOperation;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.operations.GetCafesOperation;

import java.util.List;

public class CafesFragment extends Fragment {

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
    mCafesAdapter = new CafesAdapter(getActivity());
    mRecyclerView.setAdapter(mCafesAdapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    update();
  }

  /* =========================== HIDDEN ============================= */

  private void update() {
    new GetCafesOperation()
        .addListener(new AbstractOperation.Listener<List<Cafe>>() {
          @Override
          public void onComplete(@Nullable List<Cafe> result) {
            Log.d("GUB", "#" + result);
            if (result != null) {
              mCafesAdapter.set(result);
            }
          }
        }).execute();
  }

}
