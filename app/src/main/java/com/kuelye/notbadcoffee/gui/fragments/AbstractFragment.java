package com.kuelye.notbadcoffee.gui.fragments;

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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.kuelye.notbadcoffee.R;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.kuelye.notbadcoffee.gui.helpers.NavigateHelper.setTransitionName;

public abstract class AbstractFragment extends Fragment {

  private boolean onViewCreatedCalled = false;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (!onViewCreatedCalled) {
      onViewCreatedCalled = true;

      onBeforeViewShowed();
    }

    super.onViewCreated(view, savedInstanceState);
  }

  protected void onBeforeViewShowed() {
    // stub
  }

}
