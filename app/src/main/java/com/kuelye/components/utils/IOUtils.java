package com.kuelye.components.utils;

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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {

  private static final int BUFFER_SIZE = 1024;

  public static String readFullyAndCloseSilently(
      @NonNull final InputStream in
      , @NonNull final String encoding)
      throws IOException {
    try {
      return new String(readFully(in), encoding);
    } finally {
      closeSilently(in);
    }
  }

  public static void closeSilently(
      @Nullable final Closeable c) {
    if (c == null) {
      return;
    }

    try {
      c.close();
    } catch (IOException e) {
      // ignore
    }
  }

  /* ========================== HIDDEN ============================== */

  private static byte[] readFully(
      @NonNull final InputStream in)
      throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final byte[] buffer = new byte[BUFFER_SIZE];
    int length;
    while ((length = in.read(buffer)) != -1) {
      baos.write(buffer, 0, length);
    }

    return baos.toByteArray();
  }

}
