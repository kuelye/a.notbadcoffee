package com.kuelye.notbadcoffee.logic.parsers.string;

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

import com.kuelye.notbadcoffee.logic.parsers.UnsupportedParseValue;
import com.kuelye.notbadcoffee.model.Link;
import com.kuelye.notbadcoffee.model.Link.Type;

import static com.kuelye.notbadcoffee.model.Link.Type.FACEBOOK;
import static com.kuelye.notbadcoffee.model.Link.Type.INSTAGRAM;
import static com.kuelye.notbadcoffee.model.Link.Type.VK;

public class LinkStringParser extends AbstractStringParser<Link> {

  private static final String PREFIX_VK = "https://vk.com/";
  private static final String PREFIX_INSTAGRAM = "https://instagram.com/";
  private static final String PREFIX_FACEBOOK = "https://www.facebook.com/";

  @Override
  @NonNull public Link parse(@NonNull String linkAsString) throws Exception {
    Type type = null;
    if (linkAsString.startsWith(PREFIX_VK)) {
      type = VK;
    } else if (linkAsString.startsWith(PREFIX_INSTAGRAM)) {
      type = INSTAGRAM;
    } else if (linkAsString.startsWith(PREFIX_FACEBOOK)) {
      type = FACEBOOK;
    }

    if (type == null) {
      throw new UnsupportedParseValue(linkAsString);
    } else {
      return new Link(type, linkAsString);
    }
  }

}
