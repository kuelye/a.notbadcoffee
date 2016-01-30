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
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.model.MenuRow;

public class MenuAdapter extends ArrayAdapter<MenuRow> {

  public MenuAdapter(@NonNull Context context) {
    super(context, R.layout.cafe_menu_row);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.cafe_menu_row, parent, false);
      final RowViewHolder rowViewHolder = new RowViewHolder(convertView);
      convertView.setTag(rowViewHolder);
    }

    final RowViewHolder holder = (RowViewHolder) convertView.getTag();
    final MenuRow menuRow = getItem(position);
    holder.itemTextView.setText(menuRow.getItem());
    holder.costTextView.setText(menuRow.getCost());

    return convertView;
  }

  /* =========================== INNER ============================== */

  static class RowViewHolder {

    @NonNull public final TextView itemTextView;
    @NonNull public final TextView costTextView;

    public RowViewHolder(@NonNull View rowView) {
      itemTextView = (TextView) rowView.findViewById(R.id.cafe_menu_item_text_view);
      costTextView = (TextView) rowView.findViewById(R.id.cafe_menu_cost_text_view);
    }

  }

}
