package com.kuelye.components.concurrent;

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

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.setThreadPriority;

class ThreadFactory implements java.util.concurrent.ThreadFactory {

  @Override
  public Thread newThread(Runnable r) {
    return new Thread(r);
  }

  /* ============================ INNER ============================= */

  private class Thread extends java.lang.Thread {

    public Thread(Runnable r) {
      super(r);
    }

    @Override
    public void run() {
      setThreadPriority(THREAD_PRIORITY_BACKGROUND);

      super.run();
    }

  }

}
