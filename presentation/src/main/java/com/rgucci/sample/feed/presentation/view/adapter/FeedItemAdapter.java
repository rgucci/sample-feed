/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Adaptar that manages a collection of {@link FeedItemModel}.
 */
public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedItemViewHolder> {

  public interface OnItemClickListener {
    void onFeedItemClicked(FeedItemModel feedItemModel);
  }

  private List<FeedItemModel> feedItemsList;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  public FeedItemAdapter(Context context) {
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.feedItemsList = Collections.emptyList();
  }

  @Override public int getItemCount() {
    return (this.feedItemsList != null) ? this.feedItemsList.size() : 0;
  }

  @Override public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_feed_item, parent, false);
    return new FeedItemViewHolder(view);
  }

  @Override public void onBindViewHolder(FeedItemViewHolder holder, final int position) {
    final FeedItemModel feedItemModel = this.feedItemsList.get(position);
    holder.textViewTitle.setText(feedItemModel.getDescription());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (FeedItemAdapter.this.onItemClickListener != null) {
          FeedItemAdapter.this.onItemClickListener.onFeedItemClicked(feedItemModel);
        }
      }
    });
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setFeedItemsList(Collection<FeedItemModel> feedItemsList) {
    this.validateFeedItemsCollection(feedItemsList);
    this.feedItemsList = (List<FeedItemModel>) feedItemsList;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateFeedItemsCollection(Collection<FeedItemModel> feedItemCollection) {
    if (feedItemCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class FeedItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.title) TextView textViewTitle;

    public FeedItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
