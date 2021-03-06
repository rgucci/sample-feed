/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.rgucci.sample.feed.domain.Category;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.internal.di.components.FeedComponent;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;
import com.rgucci.sample.feed.presentation.presenter.FeedPresenter;
import com.rgucci.sample.feed.presentation.view.FeedView;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemAdapter;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemHorizontalAdapter;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemLayoutManager;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemVerticalAdapter;
import com.rgucci.sample.feed.presentation.view.component.EndlessRecyclerScrollListener;

import javax.inject.Inject;
import java.util.List;

/**
 * Fragment that shows a feed of posts.
 */
public class FeedFragment extends BaseFragment implements FeedView {

    private static final String PARAM_CATEGORY = "PARAM_CATEGORY";
    private static final String PARAM_LOAD_ON_CREATE = "PARAM_LOAD_ON_CREATE";

  /**
   * Interface for listening user list events.
   */
  public interface FeedListener {
    void onUserClicked(final FeedItemModel userModel);
  }

    public static FeedFragment newInstance(final Category category, final boolean loadItemsOnCreate) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_CATEGORY, category);
        args.putBoolean(PARAM_LOAD_ON_CREATE, loadItemsOnCreate);
        fragment.setArguments(args);
        return fragment;
    }

  @Inject FeedPresenter feedPresenter;
  @Inject FeedItemVerticalAdapter feedItemVerticalAdapter;
  @Inject FeedItemHorizontalAdapter feedItemHorizontalAdapter;

  @Bind(R.id.rv_topList) RecyclerView rv_topList;
  @Bind(R.id.rv_feedlist) RecyclerView rv_users;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  private FeedListener feedListener;
    private Category category = Category.Hot;
    private boolean loadOnCreate;

  public FeedFragment() {
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof FeedListener) {
      this.feedListener = (FeedListener) activity;
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(FeedComponent.class).inject(this);

      Bundle args = getArguments();
      final Category paramCategory = (Category) args.getSerializable(PARAM_CATEGORY);
      if (paramCategory != null) {
          category = paramCategory;
      }

      loadOnCreate = args.getBoolean(PARAM_LOAD_ON_CREATE, false);

  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.feedPresenter.setView(this);
    if (savedInstanceState == null && loadOnCreate) {
      this.loadFeedItems();
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.feedPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.feedPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    rv_users.setAdapter(null);
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.feedPresenter.destroy();
  }

  @Override public void onDetach() {
    super.onDetach();
    this.feedListener = null;
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void renderUserList(List<FeedItemModel> userModelCollection) {
    if (userModelCollection != null) {
        this.feedItemVerticalAdapter.setFeedItemsList(userModelCollection);
        this.feedItemHorizontalAdapter.setFeedItemsList(userModelCollection);
    }
  }

  @Override public void viewUser(FeedItemModel userModel) {
    if (this.feedListener != null) {
      this.feedListener.onUserClicked(userModel);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return this.getActivity().getApplicationContext();
  }

  private void setupRecyclerView() {
      final FeedItemLayoutManager topLayoutManager = new FeedItemLayoutManager(context(), LinearLayoutManager.HORIZONTAL);
      rv_topList.setLayoutManager(topLayoutManager);
      rv_topList.setAdapter(feedItemHorizontalAdapter);

    this.feedItemVerticalAdapter.setOnItemClickListener(onItemClickListener);
    final FeedItemLayoutManager layoutManager = new FeedItemLayoutManager(context(), LinearLayoutManager.VERTICAL);
    this.rv_users.setLayoutManager(layoutManager);
    this.rv_users.setAdapter(feedItemVerticalAdapter);
    this.rv_users.setOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
        @Override
        public void onLoadMore(final int currentPage) {
            feedPresenter.getNextPage(currentPage);
        }
    });

      rv_topList.setOnScrollListener(new EndlessRecyclerScrollListener(topLayoutManager) {
          @Override
          public void onLoadMore(final int current_page) {
              feedPresenter.getNextPage(current_page);
          }
      });
  }

  /**
   * Loads all feedItems.
   */
  public void loadFeedItems() {
    this.feedPresenter.setCategory(category);
    this.feedPresenter.initialize();
  }

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    FeedFragment.this.loadFeedItems();
  }

  private FeedItemAdapter.OnItemClickListener onItemClickListener =
      new FeedItemAdapter.OnItemClickListener() {
        @Override public void onFeedItemClicked(FeedItemModel userModel) {
          if (FeedFragment.this.feedPresenter != null && userModel != null) {
            FeedFragment.this.feedPresenter.onUserClicked(userModel);
          }
        }
      };
}
