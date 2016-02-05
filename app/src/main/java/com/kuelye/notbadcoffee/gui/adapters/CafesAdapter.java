package com.kuelye.notbadcoffee.gui.adapters;

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

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.fragments.AbstractBaseFragment;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Cafes;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

import static butterknife.ButterKnife.bind;
import static com.kuelye.components.utils.AndroidUtils.getActionBarHeight;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;
import static com.kuelye.notbadcoffee.Application.getLastLocation;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillHeaderLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillLocationLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillMenuLayout;
import static com.kuelye.notbadcoffee.gui.helpers.CafeHelper.fillTimetableLayout;

public class CafesAdapter extends RecyclerView.Adapter<CafesAdapter.RowViewHolder> {

  @NonNull private final Context mContext;
  @NonNull private final Callback mCallback;

  @NonNull private final Object mLock = new Object();
  @NonNull private Cafes mCafes = new Cafes();

  public CafesAdapter(@NonNull final Context context, @NonNull final Callback callback) {
    mContext = context;
    mCallback = callback;
  }

  @Override
  public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cafe_row, parent, false);

    return new RowViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final RowViewHolder holder, int position) {
    final Cafe cafe = mCafes.get(position);

    fillHeaderLayout(mContext, holder.headerLayout, cafe, null);
    fillLocationLayout(mContext, holder.placeLayout, cafe, getLastLocation());
    fillMoreInfoHeaderLayout(holder, cafe);
    fillMenuLayout(mContext, holder.menuLayout, cafe);
    fillTimetableLayout(mContext, holder.timetableLayout, cafe);

    holder.placeLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCallback.onLocationClicked(holder, cafe);
      }
    });
    holder.photoClickableImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCallback.onPhotoClicked(holder, cafe);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mCafes.size();
  }

  @Subscribe
  public void onLocationGotten(AbstractBaseFragment.OnLocationGottenEvent event) {
    notifyDataSetChanged();
  }

  public void setCafes(@NonNull Cafes cafes) {
    synchronized (mLock) {
      mCafes = cafes;
    }

    notifyDataSetChanged();
  }

  @NonNull public Cafes getCafes() {
    return mCafes;
  }

  /* =========================== HIDDEN ============================= */

  private void fillMoreInfoHeaderLayout(@NonNull final RowViewHolder holder, @NonNull Cafe cafe) {
    if (cafe.getTimetable() == null) {
      holder.openUntilTextView.setText(null);
    } else {
      holder.openUntilTextView.setText(cafe.getTimetable().getOpenUntilDisplayString(mContext));
    }
    holder.moreInfoHeaderLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (holder.moreInfoLayout.getVisibility() == View.GONE) {
          holder.moreInfoLayout.setVisibility(View.VISIBLE);
          holder.moreInfoHeaderIconImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
        } else {
          holder.moreInfoLayout.setVisibility(View.GONE);
          holder.moreInfoHeaderIconImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
        }
      }
    });
  }

  /* =========================== INNER ============================== */

  public interface Callback {
    void onLocationClicked(@NonNull RowViewHolder cafeRowViewHolder, @NonNull Cafe cafe);
    void onPhotoClicked(@NonNull RowViewHolder cafeRowViewHolder, @NonNull Cafe cafe);
  }

  public static class HeaderDecoration extends RecyclerView.ItemDecoration {

    private final int mHeight;

    public HeaderDecoration(@NonNull Context context) {
      mHeight = getStatusBarHeight(context) + getActionBarHeight(context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view
        , RecyclerView parent, RecyclerView.State state) {
      if (parent.getChildAdapterPosition(view) == 0) {
        outRect.top = mHeight;
      } else {
        outRect.setEmpty();
      }
    }

  }

  public static class RowViewHolder extends RecyclerView.ViewHolder {

    public final View rootView;
    @Bind(R.id.cafe_header_layout) public ViewGroup headerLayout;
    @Bind(R.id.cafe_name_and_links_toolbar) public Toolbar nameAndLinksToolbar;
    @Bind(R.id.cafe_photo_clickable_image_view) public ImageView photoClickableImageView;
    @Bind(R.id.cafe_photo_scrim_layout) public ViewGroup photoScrimLayout;
    @Bind(R.id.cafe_photo_image_view) public ImageView photoImageView;
    @Bind(R.id.cafe_place_layout) public ViewGroup placeLayout;
    @Bind(R.id.cafe_more_info_layout) public ViewGroup moreInfoLayout;
    @Bind(R.id.cafe_more_info_header_layout) public ViewGroup moreInfoHeaderLayout;
    @Bind(R.id.cafe_open_until_text_view) public TextView openUntilTextView;
    @Bind(R.id.cafe_more_info_header_icon_image_view) public ImageView moreInfoHeaderIconImageView;
    @Bind(R.id.cafe_timetable_layout) public ViewGroup timetableLayout;
    @Bind(R.id.cafe_menu_layout) public ViewGroup menuLayout;

    public RowViewHolder(@NonNull View view) {
      super(view);

      rootView = view;
      bind(this, view);
    }

  }

}
