package com.bingr.app.net_utils;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * A cache class for containing bitmap information.
 *
 * @author akfrank
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache{

    /**
     * Gets the default cache size/
     * @return default cache size
     */
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    /**
     * Creates a new LruBitmapCache with the default size.
     */
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    /**
     * Creates a new LruBitmapCache of the specified size.
     * @param sizeInKiloBytes The size of the cache in kilobytes.
     */
    public LruBitmapCache(int sizeInKiloBytes){
        super(sizeInKiloBytes);
    }

    /**
     * Gets the size of a bitmap in kilobytes.
     *
     * @param key The key for this bitmap (url)
     * @param value The bitmap to be measured
     * @return size of the bitmap in kilobytes
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    /**
     * Gets a bitmap from within the cache
     * @param url the url of the bitmap
     * @return The bitmap
     */
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    /**
     * Places a bitmap in the cache
     * @param url The url of the bitmap
     * @param bitmap the bitmap to be placed
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

