/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rgucci.sample.feed.domain;

/**
 * Class that represents a FeedItem in the domain layer.
 */
public class FeedItem {

  private final String id;

  public FeedItem(String id) {
    this.id = id;
  }

  private String coverUrl;
  private String description;

  public String getId() {
    return id;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** FeedItem Details *****\n");
    stringBuilder.append("id=" + this.getId() + "\n");
    stringBuilder.append("cover url=" + this.getCoverUrl() + "\n");
    stringBuilder.append("description=" + this.getDescription() + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
