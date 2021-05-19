package com.bingr.app.auth;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.ResponseObserver;
import com.bingr.app.app.AppController;
import com.bingr.app.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuthRequest {

    private ArrayList<ResponseObserver> presenters = new ArrayList<>();

    public void setPresenter(ResponseObserver presenter){
        this.presenters.add(presenter);
    }

    public void loginRequest(String email, String password){
        String url = Const.URL_LOGIN + '/' + email + '/' + password;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for(ResponseObserver presenter: presenters) {
                        presenter.update(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    for(ResponseObserver presenter: presenters) {
                        presenter.update(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // send the request
        AppController.getInstance().addToRequestQueue(req, "login");
    }

    public void registerRequest(JSONObject userData){
        // create new request
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Const.URL_REGISTER,
                userData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for(ResponseObserver presenter: presenters) {
                        presenter.update(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    for(ResponseObserver presenter: presenters) {
                        presenter.update(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // send the request
        AppController.getInstance().addToRequestQueue(req, "register");
    }
}