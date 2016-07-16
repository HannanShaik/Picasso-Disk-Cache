package com.hs.picassodiskcache.image_cache;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Hannan Shaik on 03/05/16.
 */
public class ClearCacheService extends IntentService {

    public ClearCacheService() {
        super("ClearCacheService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(ClearCacheService.class.getSimpleName(),"Service started to clear image cache");
        CacheUtils.clearCache(this);
    }
}
