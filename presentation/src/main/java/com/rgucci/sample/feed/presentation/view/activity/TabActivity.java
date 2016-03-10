package com.rgucci.sample.feed.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import com.rgucci.sample.feed.domain.Category;
import com.rgucci.sample.feed.presentation.R;
import com.rgucci.sample.feed.presentation.internal.di.HasComponent;
import com.rgucci.sample.feed.presentation.internal.di.components.DaggerFeedComponent;
import com.rgucci.sample.feed.presentation.internal.di.components.FeedComponent;
import com.rgucci.sample.feed.presentation.view.fragment.FeedFragment;

/**
 * Created by russell.gutierrez on 10/3/16.
 */
public class TabActivity extends BaseActivity implements HasComponent<FeedComponent> {

    private static final Category tabCategories[] = {Category.Fresh, Category.Hot, Category.Trending};
    private FeedComponent feedComponent;
    private FeedFragment[] fragments = new FeedFragment[tabCategories.length];

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TabActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initializeInjector();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                if (fragments[position] == null) {
                    fragments[position] = FeedFragment.newInstance(tabCategories[position], position == 0);
                }
                return fragments[position];
            }

            @Override
            public int getCount() {
                return tabCategories.length;
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                fragments[position].loadFeedItems();
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
                if (fragments[tab.getPosition()] != null) {
                    fragments[tab.getPosition()].loadFeedItems();
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        for (int i = 0; i < tabCategories.length; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabCategories[i].toString())
                            .setTabListener(tabListener));
        }

    }

    @Override public FeedComponent getComponent() {
        return feedComponent;
    }
    private void initializeInjector() {
        this.feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

}
