package com.hs.picassodiskcache.image_cache;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Hannan Shaik on 13/04/16.
 */
public class CacheUtils {

    private static final String PICASSO_CACHE = "picasso-cache";

    /**
     * Creates a dir for picasso under default cache dir of the app
     * @param context
     * @return
     */
    public static File createDefaultCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

    /**
     * Calculates the disk cache size based on available memory
     * @param dir
     * @return
     */
    public static long calculateDiskCacheSize(File dir) {
        long size = 5 * 1024 * 1024;
        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            // Target 2% of the total space.
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }
        // Bound inside min/max size for disk cache.
        return Math.max(Math.min(size, 50 * 1024 * 1024), 5 * 1024 * 1024);
    }

    /**
     * Force evicts the image cache by deleting the whole picasso-cache folder.
     * @param context
     */
    public static void clearCache(Context context) {
        final File cache = new File(
                context.getApplicationContext().getCacheDir(),
                PICASSO_CACHE);
        if (cache.exists()) {
            deleteFolder(cache);
        }
    }

    private static void deleteFolder(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles())
                deleteFolder(child);
        }
        fileOrDirectory.delete();
    }

    /**
     * Invokes the service to cache the images
     * @param context
     * @param imageUrls list of image urls of images to be cached.
     */
    public static void cacheImages(Context context, ArrayList<String> imageUrls){
        Intent imageCachingService = new Intent(context, ImageCachingService.class);
        imageCachingService.putStringArrayListExtra("image-urls", imageUrls);
        context.startService(imageCachingService);
    }

    /**
     * Schedules an alarm manager for clearing the cache at 8 PM every day.
     */
    public static void scheduleClearingCache(Context context){
        Log.d(CacheUtils.class.getSimpleName(), "Scheduling alarm for clearing cache");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 00);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ClearCacheService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
