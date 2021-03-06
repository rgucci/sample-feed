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
package com.rgucci.sample.feed.presentation.internal.di.modules;

import com.rgucci.sample.feed.domain.interactor.GetFeedItems;
import com.rgucci.sample.feed.domain.interactor.UseCase;
import com.rgucci.sample.feed.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * Dagger module that provides feed related collaborators.
 */
@Module
public class FeedModule {

  public FeedModule() {}

  @Provides @PerActivity @Named("feedItemList")
  UseCase provideFeedItemList(
          GetFeedItems getFeedItems) {
    return getFeedItems;
  }

}