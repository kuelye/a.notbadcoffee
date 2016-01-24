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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

public abstract class AbstractOperation<R> implements Callable<R> {

  @Nullable private R mResult;

  @NonNull private final List<Listener<R>> mListeners = new CopyOnWriteArrayList<>();

  public abstract void doCall() throws Exception;

  public Future<R> execute() {
    return ThreadPoolExecutor.getInstance().submit(this);
  }

  @Override
  public final R call() {
    try {
      doCall();
    } catch (Exception e) {
      Log.d("GUB", "& ", e); // TODO
    } finally {
      onComplete();
    }

    return getResult();
  }

  public AbstractOperation<R> addListener(
      @NonNull final Listener<R> listener) {
    mListeners.add(listener);

    return this;
  }

  protected void setResult(@Nullable R result) {
    mResult = result;
  }

  @Nullable public R getResult() {
    return mResult;
  }

  /* =========================== HIDDEN ============================= */

  private void onComplete() {
    for (Listener<R> listener : mListeners) {
      listener.onComplete(mResult);
    }
  }

  /* =========================== INNER ============================== */

  public interface Listener<R> {
    void onComplete(R result);
  }

}
