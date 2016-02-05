package com.kuelye.notbadcoffee.model;

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

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.kuelye.notbadcoffee.R;

public class Link {

  @NonNull private Type mType;
  @NonNull private String mUrl;

  public Link(@NonNull Type type, @NonNull String url) {
    mType = type;
    mUrl = url;
  }

  @NonNull public Type getType() {
    return mType;
  }

  @NonNull public String getUrl() {
    return mUrl;
  }

  @Override
  public String toString() {
    return "Link{" +
        "mType=" + mType +
        ", mUrl='" + mUrl + '\'' +
        '}';
  }

  /* ================================================================ */

  public enum Type {

    VK(R.string.link_type_vk, R.drawable.ic_vk_box_white_24dp),
    INSTAGRAM(R.string.link_type_instagram, R.drawable.ic_post_instagram_white_24dp),
    FACEBOOK(R.string.link_type_facebook, R.drawable.ic_post_facebook_white_24dp);

    @StringRes private final int mDisplayStringResource;
    @DrawableRes private final int mIconResource;

    Type(@StringRes int displayStringResource, @DrawableRes int iconResource) {
      mDisplayStringResource = displayStringResource;
      mIconResource = iconResource;
    }

    @StringRes public int getDisplayStringResource() {
      return mDisplayStringResource;
    }

    @DrawableRes public int getIconResource() {
      return mIconResource;
    }
  }

}
