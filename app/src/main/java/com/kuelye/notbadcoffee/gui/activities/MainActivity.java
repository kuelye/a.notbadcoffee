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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.gui.fragments.CafesFragment;
import com.kuelye.notbadcoffee.gui.fragments.MapFragment;

import static com.kuelye.components.utils.AndroidUtils.getStatusBarHeight;

public class MainActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main_activity);

    if (savedInstanceState == null) {
      final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
      final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
      viewPager.setAdapter(viewPagerAdapter);

      // translucent status bar bug fix
      // (it height didn't included, so it is considered as 0dp)
      final View stubView = findViewById(R.id.stub_view);
      stubView.setPadding(0, getStatusBarHeight(this), 0, 0);

      final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitle(R.string.application_name);
    }
  }

  /* =========================== INNER ============================== */

  private class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT = 2;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return new CafesFragment();
        case 1:
          return new SupportMapFragment();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return COUNT;
    }

  }

}
