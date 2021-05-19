package com.bingr.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bingr.app.app.AppController;
import com.bingr.app.net_utils.Const;
import com.bingr.app.security.LoginValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic activity class for edit_profile.xml. It handles sending the requests
 * for edits and page navigation.
 *
 * @author kstrozin
 */
public class EditProfile extends AppCompatActivity {

    ImageButton FilmRoll;

    /**
     * Sets up the logic and text accessors for the EditProfile view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Button to go back to the swiping screen
        FilmRoll = (ImageButton) findViewById(R.id.FilmRoll);
        FilmRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSwipingScreen(); // Calls the function to switch back to swiping screen
            }
        });
        ImageButton ChatBubble = (ImageButton) findViewById(R.id.ChatBubble);
        ChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChatHome(); // Calls the function to switch back to swiping screen
            }
        });

        Button Exit = (Button) findViewById(R.id.exitButton);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToViewProfile(); // Calls the function to switch back to swiping screen
            }
        });
        ImageButton BingrLogo = (ImageButton) findViewById(R.id.BingrLogo);
        BingrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddFriends(); // Calls the function to switch back to swiping screen
            }
        });

        // Variables for the bio entry and display
        EditText EditBio = (EditText) findViewById(R.id.editBio);
        EditBio.setText("Test");

        TextView BioDisplay = (TextView) findViewById(R.id.bioDisplay);
        Button UpdateBio = (Button) findViewById(R.id.updateButton);
        TextView ErrorField = (TextView) findViewById(R.id.errorText);
        TextView ErrorField2 = (TextView) findViewById(R.id.errorText2);

        TextView UsernameDisplay = (TextView) findViewById(R.id.UsernameDisplay);
        EditText EditUsername = (EditText) findViewById(R.id.editUsername);
        Button UpdateUsername = (Button) findViewById(R.id.updateUsername);

        String tempURL = Const.URL_REGISTER + "/" + getIntent().getStringExtra("emailId");

        /*
            SENDS REQUEST TO THE BACKEND DISPLAY USERNAME ON THE PROFILE PAGE
        */
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
                        EditBio.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, "displayBio");


        BioURL += "/";

        final String[] finalBioURL = {BioURL};
        UpdateBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fill request parameters
                Map<String, String> params = new HashMap<>();
                String tempBio = "\"" + EditBio.getText().toString() + "\"";
                params.put("bio", tempBio);

                finalBioURL[0] += EditBio.getText().toString();

                // create new request
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, finalBioURL[0],
                        new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        errorReportField.setText(R.string.connectionFailure);
                    }
                });

                // send the request
                AppController.getInstance().addToRequestQueue(req, "updateBio");
                BioDisplay.setText(EditBio.getText().toString());
                ErrorField.setText("");
                ErrorField2.setText("");
            }
        });

        /*
            UPDATES THE USERNAME IN THE BACK END
         */
        UpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fill request parameters
                Map<String, String> params = new HashMap<>();
                String tempUsername = EditUsername.getText().toString();
                params.put("newUsername", tempUsername);

                String usernameURL = Const.URL_REGISTER + "/username/" + getIntent().getStringExtra("emailId") + "/" + tempUsername;

                // create new request
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, usernameURL,
                        new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("message").equals("success")){
                                UsernameDisplay.setText(tempUsername);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        errorReportField.setText(R.string.connectionFailure);
                    }
                });

                // send the request
                AppController.getInstance().addToRequestQueue(req, "updateUsername");
                UsernameDisplay.setText(tempUsername);
                ErrorField.setText("");
                ErrorField2.setText("");
            }
        });
    }

    /**
    *   Sets a new intent for the swiping screen and then goes to that page
    **/
    private void moveToSwipingScreen() {
        Intent intent = new Intent(this, SwipeScreen.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
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
    private void nothing(){

    }
}