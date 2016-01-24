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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;

import java.util.ArrayList;
import java.util.List;

public class CafesAdapter extends RecyclerView.Adapter<CafesAdapter.ViewHolder> {

  private final Object mLock = new Object();
  private List<Cafe> mCafes = new ArrayList<>();

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cafe_row, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final Cafe cafe = mCafes.get(position);
    holder.nameTextView.setText(cafe.getName());
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

  static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;

    public ViewHolder(View view) {
      super(view);

      nameTextView = (TextView) view.findViewById(R.id.cafe_row_name_textview);
    }

  }

}
