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
package com.rgucci.sample.feed.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * FeedItem Entity used in the data layer.
 */
public class FeedItemEntity {

  @SerializedName("id")
  private String id;

  @SerializedName("caption")
  private String caption;

  @SerializedName("thumbnailUrl")
  private String thumbnailUrl;

  public FeedItemEntity() {
    //empty
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(final String caption) {
    this.caption = caption;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(final String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** FeedItem Entity Details *****\n");
    stringBuilder.append("id=" + this.getId() + "\n");
    stringBuilder.append("caption=" + this.getCaption() + "\n");
    stringBuilder.append("thumbnailUrl=" + this.getThumbnailUrl() + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
