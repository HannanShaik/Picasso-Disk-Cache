package com.hs.picassodiskcache.image_cache;

import android.app.IntentService;
import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Hannan Shaik on 19/04/16.
 */
public class ImageCachingService extends IntentService {

    public ImageCachingService() {
        super("ImageCachingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getExtras() == null)
            return;

        ArrayList<String> imageUrlList = intent.getExtras().getStringArrayList("image-urls");
        if(imageUrlList == null)
            return;

        for(String imageUrl : imageUrlList) {
            /**
             * For each of the image url, call the fetch() method.
             *
             * Fetch Method => Asynchronously fulfills the request without a {@link ImageView} or {@link Target}. This is
             * useful when you want to warm up the cache with an image.
             */
            Picasso.with(this).load(imageUrl).fetch();
        }
    }
}
