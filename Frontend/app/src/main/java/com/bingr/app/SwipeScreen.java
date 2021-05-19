package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.app.AppController;
import com.bingr.app.models.MovieModel;
import com.bingr.app.net_utils.Const;
import com.bingr.app.net_utils.Credentials;
import com.bingr.app.net_utils.MovieApi;
import com.bingr.app.security.LoginValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SwipeScreen extends AppCompatActivity {

    // variables for movie lookups
    public int idx = 0;
    public int page = 1;
    // central image of the movie poster
    private ImageView poster;
    private TextView errorReportField;

    /*
   Buttons for the swiping actions and navigating to other pages
    */
    private ImageButton viewProfile, dislike, like, BingrLogo, ChatBubble;
    private MovieModel currentMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_screen);


        poster = findViewById(R.id.swipeScreenPoster);
        viewProfile = (ImageButton) findViewById(R.id.UserSymbol);
        dislike = (ImageButton) findViewById(R.id.ThumbsDown);
        like = (ImageButton) findViewById(R.id.ThumbsUp);
        errorReportField = (TextView) findViewById(R.id.swipeScreenError);
        BingrLogo = (ImageButton) findViewById(R.id.BingrLogo);

        GetRetroFitResponsePopular(page + "");

        /*
            Changes pages to the viewProfile screen
         */
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToViewProfile();
            }
        });
        BingrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddFriends(); // Calls the function to switch back to swiping screen
            }
        });

        /*
            Increments the current movie
         */
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRetroFitResponsePopular(page + "");
                idx++;
                if (idx >= 20) {
                    page++;
                    idx = 0;
                }
            }
        });

        /*
            Sends a request to the backend that pairs the current user and the movie, then
            increments the current movie
         */
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new request

                String url = Const.URL_LIKE_MOVIE + '/' +
                        getIntent().getStringExtra("emailId") + '/' +  currentMovie.getID();

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url,
                        null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if (message.equals("success") || message.equals("match")) {
                                if(message.equals("match")){
                                    Log.v("Match", "Match found");
                                    onMatchShowPopup(response.getString("friend"));
                                }
                                // increment movie
                                GetRetroFitResponsePopular(page + "");
                                idx++;
                                if (idx >= 20) {
                                    page++;
                                    idx = 0;
                                }
                            }
                            else {
                                errorReportField.setText(message);
                            }
                        }
                        catch (JSONException e) {
                            errorReportField.setText(R.string.invalidResponseFormat);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorReportField.setText(R.string.connectionFailure);
                    }
                });

                // send the request
                AppController.getInstance().addToRequestQueue(req, "like");
            }
        });

        /*
         * Takes the user to a movie profile page when they click on the central image.
         */
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MovieProfile.class);
                intent.putExtra("movieID", currentMovie.getID());
                LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
            }
        });

        ChatBubble = (ImageButton) findViewById(R.id.ChatBubble);
        ChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChatHome(); // Calls the function to switch back to swiping screen
            }
        });
    }

    /*
        Uses a new intent to change pages
     */
    private void moveToViewProfile() {
        Intent intent = new Intent(this, ViewProfile.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void moveToAddFriends() {
        Intent intent = new Intent(this, AddFriends.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void moveToChatHome() {
        Intent intent = new Intent(this, ChatHomepage.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }

    private void GetRetroFitResponsePopular(String page) {

        MovieApi movieApi = AppController.getInstance().getService().getMovieApi();
        Call<MovieModel> responseCall = movieApi.searchMovie(Credentials.API_KEY, Credentials.ENGLISH, page);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if (response.code() == 200) {

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    currentMovie = movies.get(idx);

                    Log.v("Tag", "Title: " + currentMovie.getTitle());

                    AppController.getInstance().getImageLoader().get(Credentials.IMAGE_BASE_URL + "w500/" + currentMovie.getPoster(), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            poster.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            poster.setContentDescription(currentMovie.getTitle() + "Poster");
                        }
                    });

                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    public void onMatchShowPopup(String emailId){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.match_popup, null);

        // create window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView matchMessage = (TextView) popupView.findViewById(R.id.match_text);
        Button toChat = (Button) popupView.findViewById(R.id.match_chat);

        // show the popup
        popupWindow.showAtLocation(popupWindow.getContentView(), Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        matchMessage.setText("You matched with " + emailId);
        toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Chat.class);
                intent.putExtra("receiver", emailId);
                intent.putExtra("chatURLExtension", getIntent().getStringExtra("emailId"));
                LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
            }
        });
    }
}