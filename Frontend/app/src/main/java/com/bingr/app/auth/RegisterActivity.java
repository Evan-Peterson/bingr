package com.bingr.app.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bingr.app.ActivityView;
import com.bingr.app.R;
import com.bingr.app.SwipeScreen;
import com.bingr.app.app.AppController;
import com.bingr.app.net_utils.Const;
import com.bingr.app.security.EmailValidator;
import com.bingr.app.security.PasswordComplexityValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic activity class for activity_register_user.xml. It handles sending the requests
 * for new user registration.
 *
 * @author akfrank
 */
public class RegisterActivity extends AppCompatActivity implements ActivityView {

    private RegisterPresenter presenter;
    private TextView errorReportField;

    /**
     * Setup logic on a new register user activity. Adds button functionality to submit field
     * entry data as a request to the server and respond accordingly.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // create presenter and inject dependency
        if(presenter == null){
            presenter = new RegisterPresenter();
        }
        presenter.setActivity(this);

        EditText username = (EditText) findViewById(R.id.registerUsername);
        EditText first_name = (EditText) findViewById(R.id.registerName);
        EditText surname = (EditText) findViewById(R.id.registerSurname);
        EditText email = (EditText) findViewById(R.id.registerEmail);
        EditText password = (EditText) findViewById(R.id.registerPassword);
        EditText confirm_password = (EditText) findViewById(R.id.registerConfirmPassword);
        errorReportField = (TextView) findViewById(R.id.registerNotifText);

        Button register = (Button) findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // clear errors on new button press
                errorReportField.setText("");

                // validate email format
                String emailText = email.getText().toString();
                if(EmailValidator.validate(emailText)){

                    // validate password matching
                    String passwordText = password.getText().toString();
                    if(passwordText.length() < PasswordComplexityValidator.MIN_PASS_LENGTH){
                        errorReportField.setText(R.string.shortPassword);
                    }
                    else if(!passwordText.equals(confirm_password.getText().toString())){
                        errorReportField.setText(R.string.unmatchedPassword);
                    }
                    else if(!PasswordComplexityValidator.validate(passwordText)){
                        errorReportField.setText(R.string.notComplexPassword);
                    }
                    else {

                        // fill request parameters
                        Map<String, String> params = new HashMap<>();
                        params.put("name", username.getText().toString());
                        params.put("firstName", first_name.getText().toString());
                        params.put("surname", surname.getText().toString());
                        params.put("emailId", email.getText().toString());
                        params.put("password", password.getText().toString());
                        JSONObject userData = new JSONObject(params);
                        try {
                            presenter.submitRegister(userData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorReportField.setText(R.string.JSONError);
                        }
                    }
                }
                else{
                    errorReportField.setText(R.string.invalidEmail);
                }
            }
        });
    }

    @Override
    public Context getContext(){
        return getBaseContext();
    }

    @Override
    public void update(Object o) {
        if(o.getClass().getSimpleName().equals("String")) {
            errorReportField.setText((String) o);
        }
    }

    public void setPresenter(RegisterPresenter presenter) {
        this.presenter = presenter;
    }
}