package com.hs.picassodiskcache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.hs.picassodiskcache.image_cache.CacheUtils;
import java.util.ArrayList;

/**
 * Created by Hannan Shaik on 15-07-2016
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> imageUrls;
    private ListView imageListView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageUrls = new ArrayList<>();
        imageListView = (ListView) findViewById(R.id.images_list);
        listAdapter = new ListAdapter(this,imageUrls);
        imageListView.setAdapter(listAdapter);
        imageListView.setVisibility(View.GONE);
    }

    /**
     * Method to populate the image urls for loading into the list
     */
    private void populateImageUrls() {
        imageUrls.add("http://cdn3.pcadvisor.co.uk/cmsdata/features/3420161/Android_800_thumb800.jpg");
        imageUrls.add("http://images.indianexpress.com/2016/02/android-m-big.jpg");
        imageUrls.add("http://blog.mobiversal.com/wp-content/uploads/2012/01/android-dev-learning1.jpg");
    }

    /**
     * This method will populate the images into the ArrayList and then call the cacheImages method
     * that will internally cache the images into the cache directory of the app
     * @param view
     */
    public void cacheImages(View view) {
        if(!isNetworkAvailable()){
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if(imageUrls.size() == 0) {
            populateImageUrls();
        }
        CacheUtils.cacheImages(this,imageUrls);
    }


    /**
     * @param view
     */
    public void showImages(View view) {
        if(imageUrls.size() == 0){
            populateImageUrls();
        }
        listAdapter.notifyDataSetChanged();
        imageListView.setVisibility(View.VISIBLE);
    }

    /**
     * This will force evict the cached images
     */
    public void clearCache(View view) {
        CacheUtils.clearCache(this);
        imageListView.setVisibility(View.GONE);
    }

    /**
     * Checks if internet is connected.
     * @return
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
