package com.kuelye.notbadcoffee;

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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kuelye.components.utils.NetworkUtils;

import java.io.File;

import static com.kuelye.notbadcoffee.ProjectConfig.CAFES_REQUEST;

public class MainActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main_activity);

    Log.d("GUB", Uri.parse("file:///android_asset/data.json").toString());
    (new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        try {
          Log.w("GUB", "# " + NetworkUtils.getResponse(CAFES_REQUEST));
        } catch (Exception e){
          Log.e("GUB", "E ", e);
        }

        return null;
      }
    }).execute();
  }

}
