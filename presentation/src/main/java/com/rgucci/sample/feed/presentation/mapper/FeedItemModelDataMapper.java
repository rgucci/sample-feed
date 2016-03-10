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
package com.rgucci.sample.feed.presentation.mapper;

import com.rgucci.sample.feed.domain.FeedItem;
import com.rgucci.sample.feed.presentation.internal.di.PerActivity;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Mapper class used to transform {@link FeedItem} (in the domain layer) to {@link FeedItemModel} in the
 * presentation layer.
 */
@PerActivity
public class FeedItemModelDataMapper {

  @Inject
  public FeedItemModelDataMapper() {}

  /**
   * Transform a {@link FeedItem} into an {@link FeedItemModel}.
   *
   * @param feedItem Object to be transformed.
   * @return {@link FeedItemModel}.
   */
  public FeedItemModel transform(FeedItem feedItem) {
    if (feedItem == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    FeedItemModel feedItemModel = new FeedItemModel(feedItem.getId());
    feedItemModel.setCoverUrl(feedItem.getCoverUrl());
    feedItemModel.setDescription(feedItem.getDescription());

    return feedItemModel;
  }

  /**
   * Transform a Collection of {@link FeedItem} into a Collection of {@link FeedItemModel}.
   *
   * @param feedItemCollection Objects to be transformed.
   * @return List of {@link FeedItemModel}.
   */
  public List<FeedItemModel> transform(Collection<FeedItem> feedItemCollection) {
    List<FeedItemModel> feedItemModels;

    if (feedItemCollection != null && !feedItemCollection.isEmpty()) {
      feedItemModels = new ArrayList<>();
      for (FeedItem feedItem : feedItemCollection) {
        feedItemModels.add(transform(feedItem));
      }
    } else {
      feedItemModels = Collections.emptyList();
    }

    return feedItemModels;
  }
}
