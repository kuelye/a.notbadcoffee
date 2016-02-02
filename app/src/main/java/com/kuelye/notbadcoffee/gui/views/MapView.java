package com.kuelye.notbadcoffee.gui.views;

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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.GoogleMapOptions;

public class MapView extends com.google.android.gms.maps.MapView {

  private OnInterceptTouchListener mOnInterceptTouchListener;

  public MapView(Context context) {
    super(context);
  }

  public MapView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MapView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs,defStyle);
  }

  public MapView(Context context, GoogleMapOptions options) {
    super(context, options);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    if (mOnInterceptTouchListener != null) {
      return mOnInterceptTouchListener.onTouch(this, event);
    } else {
      return super.onInterceptTouchEvent(event);
    }
  }

  public void setOnInterceptTouchListener(@Nullable OnInterceptTouchListener listener) {
    mOnInterceptTouchListener = listener;
  }

  /* ========================== INNER =============================== */

  public interface OnInterceptTouchListener {
    boolean onTouch(View view, MotionEvent event);
  }

}
