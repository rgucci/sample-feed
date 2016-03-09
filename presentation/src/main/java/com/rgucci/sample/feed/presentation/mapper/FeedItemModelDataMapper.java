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

import com.rgucci.sample.feed.domain.User;
import com.rgucci.sample.feed.presentation.internal.di.PerActivity;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;
import com.rgucci.sample.feed.presentation.model.UserModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapper class used to transform {@link User} (in the domain layer) to {@link FeedItemModel} in the
 * presentation layer.
 */
@PerActivity
public class FeedItemModelDataMapper {

  @Inject
  public FeedItemModelDataMapper() {}

  /**
   * Transform a {@link User} into an {@link FeedItemModel}.
   *
   * @param user Object to be transformed.
   * @return {@link FeedItemModel}.
   */
  public FeedItemModel transform(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    FeedItemModel userModel = new FeedItemModel(user.getUserId());
    userModel.setCoverUrl(user.getCoverUrl());
    userModel.setFullName(user.getFullName());
    userModel.setEmail(user.getEmail());
    userModel.setDescription(user.getDescription());
    userModel.setFollowers(user.getFollowers());

    return userModel;
  }

  /**
   * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
   *
   * @param usersCollection Objects to be transformed.
   * @return List of {@link UserModel}.
   */
  public Collection<FeedItemModel> transform(Collection<User> usersCollection) {
    Collection<FeedItemModel> userModelsCollection;

    if (usersCollection != null && !usersCollection.isEmpty()) {
      userModelsCollection = new ArrayList<>();
      for (User user : usersCollection) {
        userModelsCollection.add(transform(user));
      }
    } else {
      userModelsCollection = Collections.emptyList();
    }

    return userModelsCollection;
  }
}
