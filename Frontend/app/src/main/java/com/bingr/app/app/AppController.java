package com.bingr.app.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bingr.app.net_utils.LruBitmapCache;

/**
 * A class meant to hold a universal request queue and other application-wide interfaces. This class
 * gets created on application start.
 *
 * @author akfrank
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private Service mService;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    /**
     * Creates a static instance of an AppController object.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Accesses the AppController
     * @return the AppController object for this application
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Creates a new request queue or returns an existing one if already created.
     * @return a RequestQueue object
     */
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public Service getService(){
        if (mService == null){
            mService = new Service();
        }

        return mService;
    }

    /**
     * Creates a new ImageLoader or returns an existing one if already creates.
     * @return an ImageLoader object
     */
    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }

        return this.mImageLoader;
    }

    /**
     * Adds a request to the RequestQueue with a custom tag.
     * @param req the request to be added
     * @param tag a tag for the request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds a request to the RequestQueue with a generic tag.
     * @param req the request to be added
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all requests with the specified tag.
     * @param tag the tag to cancel
     */
    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
