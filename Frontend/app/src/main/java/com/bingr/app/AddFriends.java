package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.app.AppController;
import com.bingr.app.security.EmailValidator;
import com.bingr.app.net_utils.Const;

import com.bingr.app.security.LoginValidator;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddFriends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);

        ImageButton FilmRoll = (ImageButton) findViewById(R.id.FilmRoll);
        ImageButton viewProfile = (ImageButton) findViewById(R.id.UserSymbol);
        FilmRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSwipingScreen();
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToViewProfile();
            }
        });
        ImageButton ChatBubble = (ImageButton) findViewById(R.id.ChatBubble);
        ChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChatHome(); // Calls the function to switch back to swiping screen
            }
        });

        EditText Email = findViewById(R.id.search);
        Button SearchEmail = (Button) findViewById(R.id.searchEmail);
        TextView FriendAdded = (TextView) findViewById(R.id.friendAdded);
        TextView ErrorText = (TextView) findViewById(R.id.errorText);

        SearchEmail.setOnClickListener(new View.OnClickListener() {
            String friendURL = Const.URL_REGISTER + "/friends/" + getIntent().getStringExtra("emailId") + "/";
            @Override
            public void onClick(View v) {
                if(EmailValidator.validate(Email.getText().toString()))
                {
                    ErrorText.setText("");
                    friendURL += Email.getText().toString();
                    final Boolean[] success = {true};
                    Map<String, String> params = new HashMap<>();
                    params.put("friends", Email.getText().toString());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, friendURL,
                            new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            success[0] = false;
                        }
                    });

                    // send the request
                    AppController.getInstance().addToRequestQueue(req, "addedFriend");
                    if (success[0]){
                        ErrorText.setText("");
                        FriendAdded.setText("Friend Added");
                    }
                    else {
                        FriendAdded.setText("");
                        ErrorText.setText("User not found");
                    }
                }
                else {
                    ErrorText.setText("Please enter a valid email");
                    FriendAdded.setText("");
                }
            }
        });

    }
    private void moveToSwipingScreen() {
        Intent intent = new Intent(this, SwipeScreen.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void moveToViewProfile() {
        Intent intent = new Intent(this, ViewProfile.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void moveToChatHome() {
        Intent intent = new Intent(this, ChatHomepage.class);
        LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
    }
    private void nothing(){

    }
}
