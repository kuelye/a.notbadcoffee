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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.fragments.AbstractBaseFragment;

import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

abstract class AbstractBaseActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.base_activity);
  }

  @Override
  public void onBackPressed() {
    final AbstractBaseFragment fragment
        = (AbstractBaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    if (fragment.onBackPressed()) {
      super.onBackPressed();
    }
  }

}
