package com.bingr.app.app;

import android.util.Log;

import com.bingr.app.models.ConfigurationModel;
import com.bingr.app.net_utils.Credentials;
import com.bingr.app.net_utils.MovieApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to allow access to themoviedb API from AppController.
 *
 * @author kriggs
 */
public class Service {

	private String imageBaseURL;
	private String[] profileSizes;

	private static Retrofit.Builder retrofitBuilder =
	        new Retrofit.Builder()
	        .baseUrl(Credentials.BASE_URL)
	        .addConverterFactory(GsonConverterFactory.create());

	private static Retrofit retrofit = retrofitBuilder.build();

	private static MovieApi movieApi = retrofit.create(MovieApi.class);

	public MovieApi getMovieApi() {
		return movieApi;
	}

	public String getImageBaseURL(){
		if(imageBaseURL == null){
			Call<ConfigurationModel> responseCall = movieApi.getConfig(Credentials.API_KEY);

			responseCall.enqueue(new Callback<ConfigurationModel>() {
				@Override
				public void onResponse(Call<ConfigurationModel> call, Response<ConfigurationModel> response) {
					if (response.code() == 200) {
						imageBaseURL = response.body().getBase_url();
						profileSizes = response.body().getPoster_sizes();
					} else {
						try {
							Log.v("Tag", "Error" + response.errorBody().string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onFailure(Call<ConfigurationModel> call, Throwable t) {
					Log.v("Tag", "Error" + t.toString());
				}
			});
		}
		return imageBaseURL;
	}

	public String[] getProfileSizes(){
		if(profileSizes == null){
			Call<ConfigurationModel> responseCall = movieApi.getConfig(Credentials.API_KEY);

			responseCall.enqueue(new Callback<ConfigurationModel>() {
				@Override
				public void onResponse(Call<ConfigurationModel> call, Response<ConfigurationModel> response) {
					if (response.code() == 200) {
						imageBaseURL = response.body().getBase_url();
						profileSizes = response.body().getPoster_sizes();
					} else {
						try {
							Log.v("Tag", "Error" + response.errorBody().string());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onFailure(Call<ConfigurationModel> call, Throwable t) {
					Log.v("Tag", "Error" + t.toString());
				}
			});
		}
		return profileSizes;
	}
}
