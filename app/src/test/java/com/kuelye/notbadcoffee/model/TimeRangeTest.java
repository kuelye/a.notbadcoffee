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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeRangeTest {

  private TimeRange mTimeRangeA = new TimeRange("09:00", "23:00");
  private TimeRange mTimeRangeB = new TimeRange("00:00", "24:00");
  private TimeRange mTimeRangeC = new TimeRange("11:00", "01:00");

  @Test public void getDisplayString() {
    assertEquals("09:00 - 23:00", mTimeRangeA.getDisplayString());
    assertEquals("00:00 - 24:00", mTimeRangeB.getDisplayString());
    assertEquals("11:00 - 01:00", mTimeRangeC.getDisplayString());
  }

  @Test public void isIncludeTime() {
    assertFalse(mTimeRangeA.isInclude(8, 59));
    assertTrue(mTimeRangeA.isInclude(9, 0));
    assertFalse(mTimeRangeA.isInclude(23, 0));
    assertTrue(mTimeRangeB.isInclude(0, 0));
    assertTrue(mTimeRangeC.isInclude(0, 59));
    assertFalse(mTimeRangeC.isInclude(1, 0));
    assertTrue(mTimeRangeC.isInclude(11, 0));
  }

}
