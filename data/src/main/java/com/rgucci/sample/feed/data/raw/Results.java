package com.rgucci.sample.feed.data.raw;

/**
 * Created by russell.gutierrez on 10/3/16.
 */
public class Results {
    public final long status;
    public final String message;
    public final Data data[];

    public Results(long status, String message, Data[] data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static final class Data {

        public final String id;
        public final String caption;
        public final Images images;

        public Data(String id, String caption, Images images) {
            this.id = id;
            this.caption = caption;
            this.images = images;
        }

        public static final class Images {

            public final String small;
            public final String cover;
            public final String normal;
            public final String large;

            public Images(String small, String cover, String normal, String large) {
                this.small = small;
                this.cover = cover;
                this.normal = normal;
                this.large = large;
            }
        }
    }

}
