package com.hs.picassodiskcache.image_cache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hannan Shaik on 03/05/16.
 */

/**
 * This Broadcast receiver will schedule the job to clear the cache at the defined time on reboot.
 */
public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            CacheUtils.scheduleClearingCache(context);
        }
    }
}
