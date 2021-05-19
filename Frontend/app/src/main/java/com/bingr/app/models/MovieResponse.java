package com.bingr.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

	//Finding movie object
    @SerializedName("total_results")
    @Expose()
    private int total;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotal() {
        return total;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieResponse {" + "total = " + total + ", movies = " + movies + " }";
    }
}
