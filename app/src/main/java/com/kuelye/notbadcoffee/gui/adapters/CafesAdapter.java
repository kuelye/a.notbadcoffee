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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.CafePlace;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.kuelye.components.utils.AndroidUtils.getActionBarHeight;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

public class CafesAdapter extends RecyclerView.Adapter<CafesAdapter.ViewHolder> {

  private Context mContext;

  @NonNull private final Object mLock = new Object();
  @NonNull private List<Cafe> mCafes = new ArrayList<>();

  public CafesAdapter(@NonNull Context context) {
    mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cafe_row, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final Cafe cafe = mCafes.get(position);
    final CafePlace cafePlace = cafe.getPlaces().get(0);

    holder.nameTextView.setText(cafe.getName());
    final String photo = cafePlace.getPhoto();
    if (photo != null) {
      Picasso.with(mContext)
          .load(photo)
          .fit()
          .into(holder.photoImageView);
    }
    holder.locationAddressTextView.setText(cafePlace.getAddress());
    holder.locationMetroTextView.setText(cafePlace.getMetro());
  }

  @Override
  public int getItemCount() {
    return mCafes.size();
  }

  public void set(@NonNull List<Cafe> cafes) {
    synchronized (mLock) {
      mCafes = cafes;
    }

    notifyDataSetChanged();
  }

  /* =========================== INNER ============================== */

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

  static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public ImageView photoImageView;
    public TextView locationAddressTextView;
    public TextView locationMetroTextView;
    public TextView locationDistanceTextView;

    public ViewHolder(View view) {
      super(view);

      nameTextView = (TextView) view.findViewById(R.id.cafe_row_name_textview);
      photoImageView = (ImageView) view.findViewById(R.id.cafe_row_photo_imageview);
      locationAddressTextView = (TextView) view.findViewById(R.id.cafe_row_location_address_textview);
      locationMetroTextView = (TextView) view.findViewById(R.id.cafe_row_location_metro_textview);
      locationDistanceTextView = (TextView) view.findViewById(R.id.cafe_row_location_distance_textview);
    }

  }

}
