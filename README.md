# Picasso-Disk-Cache

Picasso is a powerful image downloading and caching library for Android. The caching mechanism it supports is LRU and it is only in-memory cache. Libraries like [Glide] (https://github.com/bumptech/glide), [Android Universal Image Loader Library] (https://github.com/nostra13/Android-Universal-Image-Loader) etc. provide disk level caching as well. 

Many of us like picasso for its simplicity in usage and integration. Here's a sample application which demonstrates disk caching with Picasso.


##Setup

* Copy contents of [image_cache] (https://github.com/HannanShaik/Picasso-Disk-Cache/tree/master/app/src/main/java/com/hs/picassodiskcache/image_cache) in your package.

* Add the `ClearCacheService`, `ImageCachingService` and `DeviceBootReceiver` in your manifest.

* Call the below method in your custom application class.

```
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
```

* Just call the below method with the list of image urls to cache them. That's it.

```
    CacheUtils.cacheImages(mContext,imageUrls);
```

* More functions and options can be found in [CacheUtils.java] (https://github.com/HannanShaik/Picasso-Disk-Cache/blob/master/app/src/main/java/com/hs/picassodiskcache/image_cache/CacheUtils.java) 

##Clone this project and try it out for more.

Write to [me@hannanshaik.com] (mailto:me@hannanshaik.com) for any queries or suggestions.
