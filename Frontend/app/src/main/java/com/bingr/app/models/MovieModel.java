package com.bingr.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieModel implements Parcelable{

	private String title;
    private String poster_path;
    private String release_date;
    private float vote_average;
    private int id;
    private String overview;

    public MovieModel(String title, String poster_path, String release_date, float vote_average,  int id, String overview) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.id = id;
        this.overview = overview;
    }

    public MovieModel(Parcel in) {
        this.title = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readFloat();
        this.id = in.readInt();
        this.overview = in.readString();
    }


    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };



    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public float getVote_Average(){ return vote_average; }

    public int getID() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(id);
        parcel.writeFloat(vote_average);
        parcel.writeString(overview);
    }

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
