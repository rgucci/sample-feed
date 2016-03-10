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
package com.rgucci.sample.feed.data.repository.datasource;

import android.content.Context;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.rgucci.sample.feed.data.cache.UserCache;
import com.rgucci.sample.feed.data.entity.FeedItemEntity;
import com.rgucci.sample.feed.data.entity.UserEntity;
import com.rgucci.sample.feed.data.exception.NetworkConnectionException;
import com.rgucci.sample.feed.data.raw.RawResource;
import rx.Observable;

import java.util.List;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
public class RawDataSource implements UserDataStore {

  private final RawResource rawResource;

  /**
   * Construct a {@link UserDataStore} based on raw resources.
   */
  public RawDataSource(Context context) {
    this.rawResource = new RawResource(context);
  }

  @Override public Observable<List<UserEntity>> userEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of feedItems.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @RxLogObservable
  @Override
  public Observable<List<FeedItemEntity>> feedItemEntityList() {
    return rawResource.feedItemEntityList(1);
  }

  @Override public Observable<UserEntity> userEntityDetails(final String userId) {
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
}
