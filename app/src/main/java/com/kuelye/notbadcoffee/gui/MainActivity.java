package com.kuelye.notbadcoffee.gui;

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
import android.util.Log;

import com.kuelye.components.concurrent.AbstractOperation;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.operations.GetCafesOperation;

public class MainActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main_activity);

    (new GetCafesOperation())
        .addListener(new AbstractOperation.Listener<Void>() {
          @Override
          public void onComplete(Void result) {
            Log.w("GUB", "onComplete");
          }
        }).execute();
  }

}
