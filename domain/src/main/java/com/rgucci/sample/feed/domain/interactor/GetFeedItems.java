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
package com.rgucci.sample.feed.domain.interactor;

import com.rgucci.sample.feed.domain.Category;
import com.rgucci.sample.feed.domain.FeedItem;
import com.rgucci.sample.feed.domain.executor.PostExecutionThread;
import com.rgucci.sample.feed.domain.executor.ThreadExecutor;
import com.rgucci.sample.feed.domain.repository.FeedItemRepository;
import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link FeedItem}.
 */
public class GetFeedItems extends UseCase {

  private final FeedItemRepository feedItemRepository;

  private Category category;
  private int page;

  @Inject
  public GetFeedItems(FeedItemRepository feedItemRepository, ThreadExecutor threadExecutor,
          PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.feedItemRepository = feedItemRepository;
  }

  @Override public Observable buildUseCaseObservable() {
    return this.feedItemRepository.feedItems(category, page);
  }

  public UseCase init(final Category category, final int page) {
    this.category = category;
    this.page = page;
    return this;
  }
}
