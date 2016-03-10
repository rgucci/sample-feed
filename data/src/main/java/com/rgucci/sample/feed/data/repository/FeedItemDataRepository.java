/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rgucci.sample.feed.data.repository;

import com.rgucci.sample.feed.data.entity.mapper.UserEntityDataMapper;
import com.rgucci.sample.feed.data.repository.datasource.UserDataStore;
import com.rgucci.sample.feed.data.repository.datasource.UserDataStoreFactory;
import com.rgucci.sample.feed.domain.FeedItem;
import com.rgucci.sample.feed.domain.repository.FeedItemRepository;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link FeedItemRepository} for retrieving user data.
 */
@Singleton
public class FeedItemDataRepository implements FeedItemRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Constructs a {@link FeedItemRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  public FeedItemDataRepository(UserDataStoreFactory dataStoreFactory,
          UserEntityDataMapper userEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<List<FeedItem>> feedItems() {
    //we always get all feedItems from the cloud
//    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    final UserDataStore userDataStore = this.userDataStoreFactory.createRawResourceDataStore();
    return userDataStore.feedItemEntityList()
        .map(userEntities -> this.userEntityDataMapper.transform(userEntities));
  }

}
