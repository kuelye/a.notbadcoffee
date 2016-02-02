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

import android.content.Context;

import com.kuelye.notbadcoffee.R;
import com.kuelye.notbadcoffee.logic.helpers.CalendarHelper.Calendar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.kuelye.notbadcoffee.logic.helpers.CalendarHelper.setCalendar;
import static com.kuelye.notbadcoffee.model.DayRange.WEEKDAYS;
import static com.kuelye.notbadcoffee.model.DayRange.WEEKEND;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class TimetableTest {

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Mock private Calendar mCalendarMock;
  @Mock private Context mContextMock;

  private Timetable mTimetableA;
  private Timetable mTimetableB;

  @Before public void setUp() {
    when(mContextMock.getString(R.string.cafe_open_until_template)).thenReturn("Open until %s");
    when(mContextMock.getString(R.string.cafe_time_midnight)).thenReturn("midnight");
    when(mContextMock.getString(R.string.cafe_closed)).thenReturn("Closed");
    setCalendar(mCalendarMock);

    final TimetableRow timetableARow0 = new TimetableRow(
        new DayRange(WEEKDAYS), new TimeRange("9:00", "22:00"));
    final TimetableRow timetableARow1 = new TimetableRow(
        new DayRange(WEEKEND), new TimeRange("11:00", "24:00"));
    mTimetableA = new Timetable();
    mTimetableA.add(timetableARow0);
    mTimetableA.add(timetableARow1);
    final TimetableRow timetableBRow0 = new TimetableRow(
        new DayRange(WEEKDAYS), new TimeRange("9:00", "1:00"));
    final TimetableRow timetableBRow1 = new TimetableRow(
        new DayRange(WEEKEND), new TimeRange("13:00", "6:00"));
    mTimetableB = new Timetable();
    mTimetableB.add(timetableBRow0);
    mTimetableB.add(timetableBRow1);
  }

  @Test public void getTodayTimeRangeIfOpened() {
    setupCalendarMock(MONDAY, 8, 59);
    assertNull(mTimetableA.getTodayTimeRangeIfOpened());
    setupCalendarMock(WEDNESDAY, 9, 0);
    assertEquals(new TimeRange("9:00", "22:00"), mTimetableA.getTodayTimeRangeIfOpened());
    setupCalendarMock(SATURDAY, 10, 59);
    assertNull(mTimetableA.getTodayTimeRangeIfOpened());
    setupCalendarMock(MONDAY, 5, 59);
    assertEquals(new TimeRange("13:00", "6:00"), mTimetableB.getTodayTimeRangeIfOpened());
  }

  @Test public void openUntilDisplayString() {
    setupCalendarMock(MONDAY, 8, 59);
    assertEquals("Closed", mTimetableA.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(SATURDAY, 11, 0);
    assertEquals("Open until midnight", mTimetableA.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(TUESDAY, 21, 59);
    assertEquals("Open until 22:00", mTimetableA.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(MONDAY, 0, 0);
    assertEquals("Closed", mTimetableA.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(SATURDAY, 0, 59);
    assertEquals("Open until 01:00", mTimetableB.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(SATURDAY, 9, 1);
    assertEquals("Closed", mTimetableB.getOpenUntilDisplayString(mContextMock));
    setupCalendarMock(SUNDAY, 1, 0);
    assertEquals("Open until 06:00", mTimetableB.getOpenUntilDisplayString(mContextMock));
  }

  private void setupCalendarMock(int day, int hour, int minute) {
    when(mCalendarMock.getDay()).thenReturn(day);
    when(mCalendarMock.getHour()).thenReturn(hour);
    when(mCalendarMock.getMinute()).thenReturn(minute);
  }

}
