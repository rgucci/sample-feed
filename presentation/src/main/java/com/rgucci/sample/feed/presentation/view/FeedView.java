/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view;

import com.rgucci.sample.feed.presentation.model.FeedItemModel;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link FeedItemModel}.
 */
public interface FeedView extends LoadDataView {
  /**
   * Render a user list in the UI.
   *
   * @param userModelCollection The collection of {@link FeedItemModel} that will be shown.
   */
  void renderUserList(List<FeedItemModel> userModelCollection);

  /**
   * View a {@link FeedItemModel} profile/details.
   *
   * @param userModel The user that will be shown.
   */
  void viewUser(FeedItemModel userModel);
}
