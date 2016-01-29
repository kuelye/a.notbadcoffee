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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.kuelye.notbadcoffee.R;

import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.setTransitionName;

abstract class AbstractActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.toolbar_activity);

    if (savedInstanceState == null) {
      // translucent status bar bug fix (it height didn't included, so it is considered as 0dp)
      final View stubView = findViewById(R.id.stub_view);
      stubView.setPadding(0, getStatusBarHeight(this), 0, 0);

      final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitle(R.string.application_name);
    }
  }

}