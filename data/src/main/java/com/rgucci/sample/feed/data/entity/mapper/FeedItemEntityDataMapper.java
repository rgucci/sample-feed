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
package com.rgucci.sample.feed.data.entity.mapper;

import com.rgucci.sample.feed.data.entity.FeedItemEntity;
import com.rgucci.sample.feed.data.entity.UserEntity;
import com.rgucci.sample.feed.domain.FeedItem;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link FeedItem} in the
 * domain layer.
 */
@Singleton
public class FeedItemEntityDataMapper {

  @Inject
  public FeedItemEntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link FeedItem}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link FeedItem} if valid {@link UserEntity} otherwise null.
   */
  public FeedItem transform(FeedItemEntity userEntity) {
    FeedItem feedItem = null;
    if (userEntity != null) {
      feedItem = new FeedItem(userEntity.getId());
      feedItem.setDescription(userEntity.getCaption());
      feedItem.setCoverUrl(userEntity.getThumbnailUrl());
    }

    return feedItem;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link FeedItem}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link FeedItem} if valid {@link UserEntity} otherwise null.
   */
  public List<FeedItem> transform(Collection<FeedItemEntity> userEntityCollection) {
    List<FeedItem> feedItemList = new ArrayList<>(20);
    FeedItem feedItem;
    for (FeedItemEntity userEntity : userEntityCollection) {
      feedItem = transform(userEntity);
      if (feedItem != null) {
        feedItemList.add(feedItem);
      }
    }

    return feedItemList;
  }
}
