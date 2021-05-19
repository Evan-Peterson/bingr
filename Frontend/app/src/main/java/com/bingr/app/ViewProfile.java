package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bingr.app.app.AppController;
import com.bingr.app.net_utils.Const;
import com.bingr.app.security.LoginValidator;

import org.json.JSONObject;

/**
 * A basic activity class for edit_profile.xml. It handles sending the requests
 * for edits and page navigation.
 *
 * @author kstrozin
 */
public class ViewProfile extends AppCompatActivity {

    ImageButton FilmRoll;

    /**
     * Sets up the logic and text accessors for the EditProfile view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // Button to go back to the swiping screen
        FilmRoll = (ImageButton) findViewById(R.id.FilmRoll);
        FilmRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSwipingScreen(); // Calls the function to switch back to swiping screen
            }
        });
        Button EditProfile = (Button) findViewById(R.id.editButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                moveToEditProfile();
            }
        });
        ImageButton BingrLogo = (ImageButton) findViewById(R.id.BingrLogo);
        BingrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddFriends(); // Calls the function to switch back to swiping screen
            }
        });
        ImageButton ChatBubble = (ImageButton) findViewById(R.id.ChatBubble);
        ChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChatHome(); // Calls the function to switch back to swiping screen
            }
        });
        Button AddFriends = (Button) findViewById(R.id.addFriends);
        AddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddFriends(); // Calls the function to switch back to swiping screen
            }
        });

        // Variables for the bio entry and display
        TextView BioDisplay = (TextView) findViewById(R.id.bioDisplay);
        TextView ErrorField = (TextView) findViewById(R.id.errorText);
        TextView ErrorField2 = (TextView) findViewById(R.id.errorText2);
        TextView UsernameDisplay = (TextView) findViewById(R.id.UsernameDisplay);

        /*
            SENDS REQUEST TO THE BACKEND DISPLAY USERNAME ON THE PROFILE PAGE
        */
        String tempURL = Const.URL_REGISTER + "/" + getIntent().getStringExtra("emailId");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tempURL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    UsernameDisplay.setText(response.getString("name"));
                } catch (Exception e) {
                    ErrorField.setText("Oops, there was an error. Please try again");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                ErrorField.setText("COULD NOT RETRIEVE BIO");
                ErrorField2.setText(error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(request, "displayName");

        /*
            SENDS REQUEST TO THE BACKEND TO DISPLAY BIO ON THE PROFILE PAGE
        */
        String BioURL = Const.URL_EDIT_PROFILE;
        BioURL += getIntent().getStringExtra("emailId");
        final String[] bioToDisplay = {"THIS IS A PLACEHOLDER"};
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BioURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BioDisplay.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, "displayBio");


    }
    /**
     *   Sets a new intent for the swiping screen and then goes to that page
     **/
    private void moveToSwipingScreen() {
        Intent intent = new Intent(this, SwipeScreen.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void moveToEditProfile() {
        Intent intent = new Intent(this, EditProfile.class);
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
    private void nothing(){

    }
}