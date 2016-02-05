package com.kuelye.notbadcoffee.logic.parsers.json;

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

import com.kuelye.notbadcoffee.logic.parsers.string.LinkStringParser;
import com.kuelye.notbadcoffee.model.Link;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class LinksJsonParser extends AbstractJsonArrayParser<List<Link>> {

  @Override
  @NonNull public List<Link> parse(@NonNull JSONArray linksJsonArray) throws Exception {
    final LinkStringParser linkStringParser = new LinkStringParser();
    final List<Link> links = new ArrayList<>();
    for (int i = 0; i < linksJsonArray.length(); ++i) {
      final String linkAsString = linksJsonArray.getString(i);
      final Link link = linkStringParser.parse(linkAsString);
      links.add(link);
    }

    return links;
  }

}
