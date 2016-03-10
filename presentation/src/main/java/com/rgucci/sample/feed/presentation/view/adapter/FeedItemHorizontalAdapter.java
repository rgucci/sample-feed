/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view.adapter;

import android.content.Context;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;

import javax.inject.Inject;

/**
 * Adaptar that manages a collection of {@link FeedItemModel}.
 */
public class FeedItemHorizontalAdapter extends FeedItemAdapter {

    @Inject
    public FeedItemHorizontalAdapter(final Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.col_feed_item;
    }

}
