package com.bingr.app.models;

import android.os.Parcel;

public class ConfigurationModel {
    private String base_url;
    private String[] poster_sizes;

    public ConfigurationModel(String base_url, String[] poster_sizes) {
        this.base_url = base_url;
        this.poster_sizes = poster_sizes;
    }

    public String getBase_url(){
        return base_url;
    }

    public String[] getPoster_sizes(){
        return poster_sizes;
    }

}
