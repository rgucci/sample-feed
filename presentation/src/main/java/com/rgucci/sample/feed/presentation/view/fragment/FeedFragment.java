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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.internal.di.components.FeedComponent;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;
import com.rgucci.sample.feed.presentation.presenter.FeedPresenter;
import com.rgucci.sample.feed.presentation.view.FeedView;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemAdapter;
import com.rgucci.sample.feed.presentation.view.adapter.FeedItemLayoutManager;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Fragment that shows a feed of posts.
 */
public class FeedFragment extends BaseFragment implements FeedView {

  /**
   * Interface for listening user list events.
   */
  public interface FeedListener {
    void onUserClicked(final FeedItemModel userModel);
  }

  @Inject FeedPresenter userListPresenter;
  @Inject FeedItemAdapter usersAdapter;

  @Bind(R.id.rv_users) RecyclerView rv_users;
  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
  @Bind(R.id.bt_retry) Button bt_retry;

  private FeedListener feedListener;

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
    this.userListPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserList();
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.userListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userListPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    rv_users.setAdapter(null);
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.userListPresenter.destroy();
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

  @Override public void renderUserList(Collection<FeedItemModel> userModelCollection) {
    if (userModelCollection != null) {
      this.usersAdapter.setFeedItemsList(userModelCollection);
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
    this.usersAdapter.setOnItemClickListener(onItemClickListener);
    this.rv_users.setLayoutManager(new FeedItemLayoutManager(context()));
    this.rv_users.setAdapter(usersAdapter);
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    this.userListPresenter.initialize();
  }

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    FeedFragment.this.loadUserList();
  }

  private FeedItemAdapter.OnItemClickListener onItemClickListener =
      new FeedItemAdapter.OnItemClickListener() {
        @Override public void onFeedItemClicked(FeedItemModel userModel) {
          if (FeedFragment.this.userListPresenter != null && userModel != null) {
            FeedFragment.this.userListPresenter.onUserClicked(userModel);
          }
        }
      };
}
