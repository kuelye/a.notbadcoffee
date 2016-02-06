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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.fragments.CafeFragment;
import com.kuelye.notbadcoffee.gui.fragments.CafesFragment;
import com.kuelye.notbadcoffee.gui.helpers.NavigateHelper;

import static android.app.Activity.RESULT_OK;
import static com.kuelye.notbadcoffee.gui.fragments.CafesFragment.SCROLL_TO_CAFE_PLACE_ID_EXTRA;
import static com.kuelye.notbadcoffee.gui.fragments.CafesFragment.fillArguments;
import static com.kuelye.notbadcoffee.gui.fragments.CafesFragment.newInstance;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.SELECT_CAFE_REQUEST_CODE;
import static com.kuelye.notbadcoffee.model.Place.STUB_ID;

public class MainActivity extends AbstractBaseActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      final long scrollToCafePlaceId = getIntent().getLongExtra(SCROLL_TO_CAFE_PLACE_ID_EXTRA, STUB_ID);
      final Fragment fragment = newInstance(scrollToCafePlaceId);
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment, fragment)
          .commit();
    }
  }

  @Override
  public void onNewIntent(Intent newIntent) {
    final long scrollToCafePlaceId = newIntent.getLongExtra(SCROLL_TO_CAFE_PLACE_ID_EXTRA, STUB_ID);
    final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
    fillArguments(fragment.getArguments(), scrollToCafePlaceId);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    final CafesFragment cafesFragment
        = (CafesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    cafesFragment.onActivityResult(requestCode, resultCode, data);
  }

}
