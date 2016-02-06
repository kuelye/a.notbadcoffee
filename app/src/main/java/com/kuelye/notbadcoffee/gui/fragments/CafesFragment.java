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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.kuelye.components.utils.AndroidUtils;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.adapters.CafesAdapter;
import com.kuelye.notbadcoffee.logic.tasks.GetCafesAsyncTask;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static butterknife.ButterKnife.bind;
import static com.kuelye.components.utils.AndroidUtils.getActionBarHeight;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;
import static com.kuelye.notbadcoffee.Application.getBus;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.SELECT_CAFE_REQUEST_CODE;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchCafeActivity;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.launchMapActivity;
import static com.kuelye.notbadcoffee.model.Place.STUB_ID;

public class CafesFragment extends AbstractBaseFragment implements CafesAdapter.Callback {

  public static final String SCROLL_TO_CAFE_PLACE_ID_EXTRA = "SCROLL_TO_CAFE_PLACE_ID";

  @Bind(R.id.recycler_view) protected RecyclerView mRecyclerView;
  @Bind(R.id.swipe_refresh_layout) protected SwipeRefreshLayout mSwipeRefreshLayout;

  private LinearLayoutManager mLayoutManager;
  private CafesAdapter mCafesAdapter;

  public static CafesFragment newInstance(@Nullable Long scrollToCafePlaceId) {
    final CafesFragment fragment = new CafesFragment();

    if (scrollToCafePlaceId != null) {
      final Bundle arguments = new Bundle();
      fillArguments(arguments, scrollToCafePlaceId);
      fragment.setArguments(arguments);
    }

    return fragment;
  }

  public static void fillArguments(@NonNull Bundle arguments, long scrollToCafePlaceId) {
    arguments.putLong(SCROLL_TO_CAFE_PLACE_ID_EXTRA, scrollToCafePlaceId);
  }

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

    bind(this, view);

    mLayoutManager = new LinearLayoutManager(getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.addItemDecoration(new CafesAdapter.HeaderDecoration(getActivity()));
    mCafesAdapter = new CafesAdapter(getActivity(), this);
    mRecyclerView.setAdapter(mCafesAdapter);

    mToolbar.inflateMenu(R.menu.main_activity_menu);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.view_cafes_map_action:
            launchMapActivity(getActivity(), null, null);
            return true;
          default:
            return false;
        }
      }
    });

    mSwipeRefreshLayout.setProgressViewOffset(true
        , getStatusBarHeight(getActivity())
        , getStatusBarHeight(getActivity()) + getActionBarHeight(getActivity())
            + AndroidUtils.getDimensitonInDps(getActivity(), R.dimen.padding_standard));
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        update(false);
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SELECT_CAFE_REQUEST_CODE
        && resultCode == RESULT_OK) {
      final long scrollToCafePlaceId = data.getLongExtra(SCROLL_TO_CAFE_PLACE_ID_EXTRA, STUB_ID);
      setScrollToCafePlaceId(scrollToCafePlaceId);
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    getBus().register(this);
    update(true);
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
    launchMapActivity(getActivity(), cafeRowViewHolder.rootView, cafe);
  }

  @Override
  public void onPhotoClicked(
      @NonNull CafesAdapter.RowViewHolder cafeRowViewHolder
      , @NonNull Cafe cafe) {
    launchCafeActivity(getActivity()
        , cafeRowViewHolder.headerLayout
        , cafe.getPlace().getId());
  }

  @Subscribe
  public void onGetCafesEventGotten(GetCafesAsyncTask.Event getCafesEvent) {
    final Cafes cafes = getCafesEvent.getCafes();
    if (cafes != null) {
      mCafesAdapter.setCafes(cafes);

      final long scrollToCafePlaceId = getScrollToCafePlaceId();
      if (scrollToCafePlaceId >= 0) {
        int position = mCafesAdapter.getCafes().positionByPlaceId(scrollToCafePlaceId);
        if (position >= 0) {
          mRecyclerView.scrollToPosition(position);
          setScrollToCafePlaceId(STUB_ID);
        }
      }
    }

    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Subscribe
  public void onLocationGotten(OnLocationGottenEvent event) {
    update(true);
  }

  /* =========================== HIDDEN ============================= */

  private void update(boolean useCache) {
    new GetCafesAsyncTask().execute(useCache);
  }

  private void setScrollToCafePlaceId(long scrollToCafePlaceId) {
    getArguments().putLong(SCROLL_TO_CAFE_PLACE_ID_EXTRA, scrollToCafePlaceId);
  }

  private long getScrollToCafePlaceId() {
    return getArguments().getLong(SCROLL_TO_CAFE_PLACE_ID_EXTRA, STUB_ID);
  }

}
