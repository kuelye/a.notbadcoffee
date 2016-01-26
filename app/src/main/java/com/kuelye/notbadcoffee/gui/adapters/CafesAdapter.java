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
import android.widget.ListView;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;
import com.kuelye.notbadcoffee.model.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.kuelye.components.utils.AndroidUtils.getActionBarHeight;
import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

public class CafesAdapter extends RecyclerView.Adapter<CafesAdapter.RowViewHolder> {

  private Context mContext;

  @NonNull private final Object mLock = new Object();
  @NonNull private List<Cafe> mCafes = new ArrayList<>();

  public CafesAdapter(@NonNull Context context) {
    mContext = context;
  }

  @Override
  public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cafe_row, parent, false);

    return new RowViewHolder(view, mContext);
  }

  @Override
  public void onBindViewHolder(final RowViewHolder holder, int position) {
    final Cafe cafe = mCafes.get(position);
    final Place place = cafe.getPlaces().get(0);

    holder.nameTextView.setText(cafe.getName());
    final String photo = place.getPhoto();
    if (photo != null) {
      Picasso.with(mContext)
          .load(photo)
          .fit()
          .into(holder.photoImageView);
    }
    holder.locationAddressTextView.setText(place.getAddress());
    holder.locationMetroTextView.setText(place.getMetro());
    if (cafe.getTimetable() == null) {
      holder.timetableLayout.setVisibility(GONE);
      holder.openUntilTextView.setText(null);
    } else {
      holder.timetableAdapter.clear();
      holder.timetableAdapter.addAll(cafe.getTimetable());
      holder.timetableLayout.setVisibility(VISIBLE);
      holder.openUntilTextView.setText(cafe.getTimetable().getOpenUntilDisplayString(mContext));
    }
    if (cafe.getMenu() == null) {
      holder.menuLayout.setVisibility(GONE);
    } else {
      holder.menuAdapter.clear();
      holder.menuAdapter.addAll(cafe.getMenu());
      holder.menuLayout.setVisibility(VISIBLE);
    }
    holder.infoHeaderLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (holder.infoLayout.getVisibility() == View.GONE) {
          holder.infoLayout.setVisibility(View.VISIBLE);
          holder.infoHeaderIconImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
        } else {
          holder.infoLayout.setVisibility(View.GONE);
          holder.infoHeaderIconImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
        }
      }
    });
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

  static class RowViewHolder extends RecyclerView.ViewHolder {

    public final TextView nameTextView;
    public final ImageView photoImageView;
    public final TextView locationAddressTextView;
    public final TextView locationMetroTextView;
    public final TextView locationDistanceTextView;
    public final ViewGroup infoLayout;
    public final ViewGroup infoHeaderLayout;
    public final TextView openUntilTextView;
    public final ImageView infoHeaderIconImageView;
    public final ViewGroup timetableLayout;
    public final ViewGroup menuLayout;

    public final TimetableAdapter timetableAdapter;
    public final MenuAdapter menuAdapter;

    public RowViewHolder(@NonNull View view, @NonNull Context context) {
      super(view);

      nameTextView = (TextView) view.findViewById(R.id.cafe_row_name_textview);
      photoImageView = (ImageView) view.findViewById(R.id.cafe_row_photo_imageview);
      locationAddressTextView = (TextView) view.findViewById(R.id.cafe_row_location_address_textview);
      locationMetroTextView = (TextView) view.findViewById(R.id.cafe_row_location_metro_textview);
      locationDistanceTextView = (TextView) view.findViewById(R.id.cafe_row_location_distance_textview);
      infoLayout = (ViewGroup) view.findViewById(R.id.cafe_row_info_layout);
      infoHeaderLayout = (ViewGroup) view.findViewById(R.id.cafe_row_info_header_layout);
      openUntilTextView = (TextView) view.findViewById(R.id.cafe_row_open_until_textview);
      infoHeaderIconImageView = (ImageView) view.findViewById(R.id.cafe_row_info_header_icon_imageview);
      timetableLayout = (ViewGroup) view.findViewById(R.id.cafe_row_timetable_layout);
      final ListView timetableListView
          = (ListView) view.findViewById(R.id.cafe_row_timetable_listview);
      timetableAdapter = new TimetableAdapter(context);
      timetableListView.setAdapter(timetableAdapter);
      menuLayout = (ViewGroup) view.findViewById(R.id.cafe_row_menu_layout);
      final ListView menuListView
          = (ListView) view.findViewById(R.id.cafe_row_menu_listview);
      menuAdapter = new MenuAdapter(context);
      menuListView.setAdapter(menuAdapter);
    }

  }

}
