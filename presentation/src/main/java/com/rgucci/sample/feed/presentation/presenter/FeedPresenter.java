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
package com.rgucci.sample.feed.presentation.presenter;

import android.support.annotation.NonNull;
import com.rgucci.sample.feed.domain.Category;
import com.rgucci.sample.feed.domain.FeedItem;
import com.rgucci.sample.feed.domain.exception.DefaultErrorBundle;
import com.rgucci.sample.feed.domain.exception.ErrorBundle;
import com.rgucci.sample.feed.domain.interactor.DefaultSubscriber;
import com.rgucci.sample.feed.domain.interactor.GetFeedItems;
import com.rgucci.sample.feed.domain.interactor.UseCase;
import com.rgucci.sample.feed.presentation.exception.ErrorMessageFactory;
import com.rgucci.sample.feed.presentation.internal.di.PerActivity;
import com.rgucci.sample.feed.presentation.mapper.FeedItemModelDataMapper;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;
import com.rgucci.sample.feed.presentation.view.FeedView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class FeedPresenter implements Presenter {

  private FeedView viewListView;

  private final UseCase getFeedItemsUseCase;
  private final FeedItemModelDataMapper feedItemModelDataMapper;

    private Category category = Category.Hot;
    private int currentPage = 1;
    private List<FeedItem> feedItems = new ArrayList<>();

  @Inject
  public FeedPresenter(@Named("feedItemList") UseCase getFeedItemsUseCase,
          FeedItemModelDataMapper feedItemModelDataMapper) {
    this.getFeedItemsUseCase = getFeedItemsUseCase;
    this.feedItemModelDataMapper = feedItemModelDataMapper;
  }

  public void setView(@NonNull FeedView view) {
    this.viewListView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getFeedItemsUseCase.unsubscribe();
    this.viewListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the user list.
   */
  public void initialize() {
    this.loadUserList();
  }

  /**
   * Loads all feedItems.
   */
  private void loadUserList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getFeedItems(category, currentPage);
  }

  public void onUserClicked(FeedItemModel userModel) {
    this.viewListView.viewUser(userModel);
  }

  private void showViewLoading() {
    this.viewListView.showLoading();
  }

  private void hideViewLoading() {
    this.viewListView.hideLoading();
  }

  private void showViewRetry() {
    this.viewListView.showRetry();
  }

  private void hideViewRetry() {
    this.viewListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
            errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showUsersCollectionInView(List<FeedItem> feedItemsList) {
    final List<FeedItemModel> feedItemModelList =
        this.feedItemModelDataMapper.transform(feedItemsList);
    this.viewListView.renderUserList(feedItemModelList);
  }

  private void getFeedItems(final Category category, final int page) {
    ((GetFeedItems) this.getFeedItemsUseCase).init(category, page);
    this.getFeedItemsUseCase.execute(new FeedItemListSubscriber());
  }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void getNextPage(final int page) {
        this.currentPage = page;
        getFeedItems(category, page);
    }

    private final class FeedItemListSubscriber extends DefaultSubscriber<List<FeedItem>> {

    @Override public void onCompleted() {
      FeedPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      FeedPresenter.this.hideViewLoading();
      FeedPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      FeedPresenter.this.showViewRetry();
    }

    @Override public void onNext(List<FeedItem> newFeedItems) {
        if (newFeedItems.size() > 0) {
            feedItems.addAll(newFeedItems);
            FeedPresenter.this.showUsersCollectionInView(feedItems);
        }
    }
  }
}
