package com.bingr.app.net_utils;

import com.bingr.app.models.ConfigurationModel;
import com.bingr.app.models.MovieModel;
import com.bingr.app.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

	//Search for movies
    @GET("/3/movie/popular")
    Call<MovieModel> searchMovie(
        @Query("api_key") String key,
        @Query("language") String language,
        @Query("page") String page
    );
    
    
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
    		@Path("movie_id") int id,
    		@Query("api_key") String api_key,
    		@Query("language") String language
    		);

    @GET("/configuration")
    Call<ConfigurationModel> getConfig(
            @Query("api_key") String api_key
    );
}
 