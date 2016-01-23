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

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {

  private static final int POOL_SIZE_DEFAULT = 3;
  private static final long KEEP_ALIVE_TIME_MS_DEFAULT = 0;

  private static class ThreadPoolExecutorHolder {
    static final ThreadPoolExecutor INSTANCE = new ThreadPoolExecutor();
  }

  private ThreadPoolExecutor() {
    super(POOL_SIZE_DEFAULT, POOL_SIZE_DEFAULT, KEEP_ALIVE_TIME_MS_DEFAULT
        , TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new ThreadFactory());
  }

  public static ThreadPoolExecutor getInstance() {
    return ThreadPoolExecutorHolder.INSTANCE;
  }

}
