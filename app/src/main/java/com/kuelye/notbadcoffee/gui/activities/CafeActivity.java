package com.kuelye.notbadcoffee.gui.activities;

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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.fragments.CafeFragment;

public class CafeActivity extends AbstractBaseActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      final int cafePlaceId = getIntent().getIntExtra(CafeFragment.CAFE_PLACE_ID_EXTRA, -1);
      final Fragment fragment = CafeFragment.newInstance(cafePlaceId);
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment, fragment)
          .commit();
    }
  }

}
