/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemLayoutManager;
import com.rgucci.sample.feed.presentation.view.component.EndlessRecyclerScrollListener;

import javax.inject.Inject;
import java.util.List;

/**
 * Fragment that shows a feed of posts.
 */
public class FeedFragment extends BaseFragment implements FeedView {

    private static final String PARAM_CATEGORY = "PARAM_CATEGORY";

  /**
   * Interface for listening user list events.
   */
  public interface FeedListener {
    void onUserClicked(final FeedItemModel userModel);
  }

    public static FeedFragment newInstance(final Category category) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

  @Inject FeedPresenter feedPresenter;
  @Inject FeedItemAdapter feedItemAdapter;

  @Bind(R.id.rv_users) RecyclerView rv_users;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  private FeedListener feedListener;
    private Category category = Category.Hot;

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
    if (savedInstanceState == null) {
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
        this.feedItemAdapter.setFeedItemsList(userModelCollection);
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
    this.feedItemAdapter.setOnItemClickListener(onItemClickListener);
    final FeedItemLayoutManager layoutManager = new FeedItemLayoutManager(context());
    this.rv_users.setLayoutManager(layoutManager);
    this.rv_users.setAdapter(feedItemAdapter);
    this.rv_users.setOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
      @Override
      public void onLoadMore(final int currentPage) {
        //TODO load the next pages here
          Log.d("LoadMore", "page: " + currentPage);
          feedPresenter.getNextPage(currentPage);
      }
    });
  }

  /**
   * Loads all feedItems.
   */
  private void loadFeedItems() {
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
