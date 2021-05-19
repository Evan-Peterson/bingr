package com.bingr.app.security;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.auth.LoginActivity;
import com.bingr.app.R;
import com.bingr.app.app.AppController;
import com.bingr.app.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.core.content.ContextCompat.startActivity;

public class LoginValidator{

    public static void startGatedActivity(Context context, Intent intent, String email, String pass){
        String url = Const.URL_LOGIN + '/' + email + '/' + pass;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    // move to swipe screen if successful, otherwise update display
                    if(message.equals("success")){
                        intent.putExtra("emailId", email);
                        intent.putExtra("password", pass);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(context, intent, null);
                    }
                    else{
                        Intent login = new Intent(context.getApplicationContext(), LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        login.putExtra("error", R.string.loginFailure);
                        startActivity(context, login, null);
                    }
                } catch (JSONException e) {
                    Intent login = new Intent(context.getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, login, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent login = new Intent(context.getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(context, login, null);
            }
        });

        // send the request
        AppController.getInstance().addToRequestQueue(req, "login");
    }
}
