package com.rgucci.sample.feed.data.raw;

import android.content.Context;
import android.util.Log;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.gson.Gson;
import com.rgucci.sample.feed.data.R;
import com.rgucci.sample.feed.data.entity.FeedItemEntity;
import com.rgucci.sample.feed.data.exception.NetworkConnectionException;
import com.rgucci.sample.feed.domain.Category;
import rx.Observable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by russell.gutierrez on 10/3/16.
 */
public class RawResource {

    private final Context context;
    private final Gson gson = new Gson();

    private final int[] hotPages = { R.raw.hot_1, R.raw.hot_2, R.raw.hot_3};
    private final int[] freshPages = { R.raw.fresh_1, R.raw.fresh_2, R.raw.fresh_3};
    private final int[] trendingPages = { R.raw.trending_1, R.raw.trending_2, R.raw.trending_3};
    private final int[] timelyPages = { R.raw.timely_1, R.raw.timely_2, R.raw.timely_3};

    private Map<Category, int[]> categories = new HashMap<>();

    public RawResource(final Context context) {
        this.context = context;
        categories.put(Category.Hot, hotPages);
        categories.put(Category.Fresh, freshPages);
        categories.put(Category.Trending, trendingPages);
        categories.put(Category.Timely, timelyPages);
    }

    @RxLogObservable
    public Observable<List<FeedItemEntity>> feedItemEntityList(final Category category, final int page) {
        return Observable.create(subscriber -> {
            try {
                final List<FeedItemEntity> items = getFeedItemEntityList(category, page);
                subscriber.onNext(items);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    private List<FeedItemEntity> getFeedItemEntityList(final Category category, final int page) {
        Log.d("DummyService", "getFeedItemEntityList:" + page);
        final List<FeedItemEntity> list = new ArrayList<>();

        final int[] pages = categories.get(category);

        if (pages == null || page > pages.length) {
            return list;
        }

        InputStream is = context.getResources().openRawResource(pages[page-1]);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        Results results = gson.fromJson(br, Results.class);

        for (Results.Data data : results.data) {
            FeedItemEntity feedItem = new FeedItemEntity();
            feedItem.setId(data.id);
            feedItem.setCaption(data.caption);
            feedItem.setThumbnailUrl(data.images.cover);
            list.add(feedItem);
        }
        return list;
    }
}
