package com.rgucci.sample.feed.data.raw;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fernandocejas.android10.sample.data.R;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.gson.Gson;
import com.rgucci.sample.feed.data.entity.FeedItemEntity;
import com.rgucci.sample.feed.data.entity.UserEntity;
import com.rgucci.sample.feed.data.exception.NetworkConnectionException;
import com.rgucci.sample.feed.domain.FeedItem;
import rx.Observable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by russell.gutierrez on 10/3/16.
 */
public class RawResource {

    private final Context context;
    private final Gson gson = new Gson();

    private final int[] pages = { R.raw.hot_1};

    public RawResource(final Context context) {
        this.context = context;
    }

    @RxLogObservable
    public Observable<List<FeedItemEntity>> feedItemEntityList(final int page) {
        return Observable.create(subscriber -> {
            try {
                final List<FeedItemEntity> items = getFeedItemEntityList(page);
                subscriber.onNext(items);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    private List<FeedItemEntity> getFeedItemEntityList(final int page) {
        Log.d("DummyService", "getFeedItemEntityList:" + page);
        final List<FeedItemEntity> list = new ArrayList<>();

        if (page > pages.length) {
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
