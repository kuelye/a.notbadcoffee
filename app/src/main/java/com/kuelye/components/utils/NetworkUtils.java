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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.kuelye.components.utils.IOUtils.readFullyAndCloseSilently;

public final class NetworkUtils {

  private static final String RESPONSE_ENCODING_DEFAULT = "UTF-8";

  public static String getResponse(
      @NonNull final String request)
      throws IOException {
    return getResponse(request, RESPONSE_ENCODING_DEFAULT);
  }

  public static String getResponse(
      @NonNull final String request
      , @NonNull final String responseEncoding)
      throws IOException {
    final URL url = new URL(request);
    final InputStream in = url.openConnection().getInputStream();
    return readFullyAndCloseSilently(in, responseEncoding);
  }

}
