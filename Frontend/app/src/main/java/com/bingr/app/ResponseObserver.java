package com.bingr.app;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface ResponseObserver {
    public void update(Object o) throws JSONException;
}
