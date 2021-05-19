package com.bingr.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.app.AppController;
import com.bingr.app.security.EmailValidator;
import com.bingr.app.net_utils.Const;

import com.bingr.app.security.LoginValidator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHomepage extends AppCompatActivity {

    ImageButton BingrLogo, FilmRoll, UserSymbol;
    List<TextView> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_homepage);

        FilmRoll = (ImageButton) findViewById(R.id.FilmRoll);
        UserSymbol = (ImageButton) findViewById(R.id.UserSymbol);
        BingrLogo = (ImageButton) findViewById(R.id.BingrLogo);
        FilmRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSwipingScreen();
            }
        });
        UserSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToViewProfile();
            }
        });
        BingrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddFriends();
            }
        });

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Const.URL_GET_FRIENDS + getIntent().getStringExtra("emailId"),
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    populateList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("FRIENDS", error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(request, "friends");

    }
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

    private void populateList(JSONArray friends) throws JSONException {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        setContentView(linearLayout);

        Button returnButton = new Button(this);
        returnButton.setText("Return");
        returnButton.setBackgroundColor(0xFFFF5722);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearLayout.addView(returnButton);

        for(int i = 0; i < friends.length(); i++){
            String temp = friends.getJSONObject(i).getString("emailId");
            TextView textView = new TextView(this);
            textView.setText(temp);
            textView.setTextColor(0xFF000000);
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), Chat.class);
                    intent.putExtra("receiver", temp);
                    intent.putExtra("chatURLExtension", getIntent().getStringExtra("emailId"));
                    LoginValidator.startGatedActivity(getBaseContext(), intent, getIntent().getStringExtra("emailId"), getIntent().getStringExtra("password"));
                }
            });

            linearLayout.addView(textView);
        }
    }
}