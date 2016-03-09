/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.rgucci.sample.feed.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.internal.di.HasComponent;
import com.rgucci.sample.feed.presentation.internal.di.components.DaggerFeedComponent;
import com.rgucci.sample.feed.presentation.internal.di.components.FeedComponent;
import com.rgucci.sample.feed.presentation.model.FeedItemModel;
import com.rgucci.sample.feed.presentation.view.fragment.FeedFragment;

/**
 * Activity that shows a feed of posts.
 */
public class FeedActivity extends BaseActivity implements HasComponent<FeedComponent>,
        FeedFragment.FeedListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    private FeedComponent feedComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new FeedFragment());
        }
    }

    private void initializeInjector() {
        this.feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public FeedComponent getComponent() {
        return feedComponent;
    }

    @Override public void onUserClicked(FeedItemModel userModel) {
        //TODO action when feed item is clicked
    }
}
