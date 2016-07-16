package com.hs.picassodiskcache;

import android.app.Application;

import com.hs.picassodiskcache.image_cache.CacheUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * Created by Hannan Shaik on 04/05/16.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initializePicasso();
        } catch (IOException e) {
            //something went wrong while creating the cache directory
            e.printStackTrace();
        }
    }

    /**
     * Initializes all required methods and variables for Picasso
     * @throws IOException
     */
    private void initializePicasso() throws IOException {
        File cacheDir = CacheUtils.createDefaultCacheDir(this);
        Cache cache = new Cache(cacheDir, CacheUtils.calculateDiskCacheSize(cacheDir));

        OkHttpClient picassoClient = new OkHttpClient();
        //set cache dir to the client
        picassoClient.setCache(cache);

        Picasso picasso = new Picasso.Builder(this)
                //show color indicators to indicate Memory, Network or Disk Cache
                .indicatorsEnabled(BuildConfig.DEBUG)
                //specify the downloader as OKHttpClient with the set cache dir
                .downloader(new OkHttpDownloader(picassoClient))
                //show all the logs of request and response calls for images
                .loggingEnabled(BuildConfig.DEBUG)
                .build();

        Picasso.setSingletonInstance(picasso);
    }
}
