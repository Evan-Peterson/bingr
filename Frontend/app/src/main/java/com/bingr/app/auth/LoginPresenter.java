package com.bingr.app.auth;

import android.content.Intent;
import com.android.volley.VolleyError;
import com.bingr.app.ActivityView;
import com.bingr.app.ResponseObserver;
import com.bingr.app.SwipeScreen;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.core.content.ContextCompat.startActivity;


public class LoginPresenter implements ResponseObserver {

    private String email;
    private String password;
    private AuthRequest requester;
    private ActivityView activity;

    public LoginPresenter() {
        // create requester and inject dependency
        this.requester = new AuthRequest();
        requester.setPresenter(this);
    }

    public LoginPresenter(AuthRequest requester){
        this.requester = requester;
        requester.setPresenter(this);
    }

    @Override
    public void update(Object o) throws JSONException {
        if (o.getClass().getSimpleName().equals("JSONObject")) {
            JSONObject response = (JSONObject) o;
            String message = response.getString("message");
            if(message.equals("success")){
                Intent intent = new Intent(activity.getContext(), SwipeScreen.class);
                intent.putExtra("emailId", email);
                intent.putExtra("password", password);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(activity.getContext(), intent, null);
            }
            else{
                activity.update(message);
            }
        } else {
            VolleyError error = (VolleyError) o;
            activity.update(error.toString());
        }
    }

    public void setActivity(ActivityView activity) {
        this.activity = activity;
    }

    public void submitLogin(String email, String password){
        this.email = email;
        this.password = password;
        requester.loginRequest(email, password);
    }

}