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

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.Cafe;

public class CafesAdapter extends ArrayAdapter<Cafe> {

  public CafesAdapter(Context context) {
    super(context, R.layout.cafe_row);
  }

  @SuppressLint("InflateParams")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      final LayoutInflater mLayoutInflater
          = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = mLayoutInflater.inflate(R.layout.cafe_row, null);

      final CafeViewHolder cafeViewHolder = new CafeViewHolder();
      cafeViewHolder.nameTextView
          = (TextView) convertView.findViewById(R.id.cafe_row_name_textview);
      convertView.setTag(cafeViewHolder);
    }

    final Cafe cafe = getItem(position);
    final CafeViewHolder cafeViewHolder = (CafeViewHolder) convertView.getTag();
    cafeViewHolder.nameTextView.setText(cafe.getName());

    return convertView;
  }

  /* =========================== INNER ============================== */

  private static class CafeViewHolder {

    public TextView nameTextView;

  }

}
