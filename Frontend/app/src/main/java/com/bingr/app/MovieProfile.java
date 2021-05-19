package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bingr.app.app.AppController;
import com.bingr.app.app.Service;
import com.bingr.app.models.ConfigurationModel;
import com.bingr.app.models.MovieModel;
import com.bingr.app.models.MovieResponse;
import com.bingr.app.net_utils.Credentials;
import com.bingr.app.net_utils.MovieApi;
import com.bingr.app.security.LoginValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile);

        ImageButton returnButton = (ImageButton) findViewById(R.id.movieProfileReturn);
        Button chatButton = (Button) findViewById(R.id.movieProfileChatButton);
        TextView title = (TextView) findViewById(R.id.movieProfileTitle);
        ImageView poster = (ImageView) findViewById(R.id.movieProfilePoster);
        TextView releaseDate = (TextView) findViewById(R.id.movieProfileReleaseField);
        TextView rating = (TextView) findViewById(R.id.movieProfileRatingField);
        TextView desc = (TextView) findViewById(R.id.movieProfileDesc);

        Intent intent = getIntent();
        int movieID = intent.getIntExtra("movieID", 550);
        
        Service service = AppController.getInstance().getService();
        MovieApi movieApi = service.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(movieID, Credentials.API_KEY, Credentials.ENGLISH);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel movie = response.body();
                    title.setText(movie.getTitle());
                    releaseDate.setText(movie.getReleaseDate());
                    rating.setText(movie.getVote_Average() + "");
                    desc.setText(movie.getOverview());

                    AppController.getInstance().getImageLoader().get(Credentials.IMAGE_BASE_URL + "w500/" + movie.getPoster(), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            poster.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            poster.setContentDescription(movie.getTitle() + "Poster");
                        }
                    });
                }
                else {
                    try {
                        Log.v("Tag", "Error " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Chat.class);
                intent.putExtra("receiver", movieID + "");
                intent.putExtra("chatURLExtension", "rooms/" + movieID);
                LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
            }
        });
    }
    private void nothing(){

    }
}