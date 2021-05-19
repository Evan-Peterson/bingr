package com.bingr.app.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bingr.app.ActivityView;
import com.bingr.app.R;
import com.bingr.app.SwipeScreen;
import com.bingr.app.security.EmailValidator;

import org.json.JSONException;

/**
 * A basic activity class main_login.xml. It handles sending the requests
 * for user login and responding accordingly.
 *
 * @author akfrank
 */
public class LoginActivity extends AppCompatActivity implements ActivityView {

    private LoginPresenter presenter;
    private TextView errorReportField;

    /**
     * Sets up the view fields for accessing and the login button. On a button press, a request
     * is sent to the server and it waits for a response.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        // create presenter and inject dependency
        if(presenter == null){
            presenter = new LoginPresenter();
        }
        presenter.setActivity(this);

        Button login = (Button) findViewById(R.id.loginButton);
        EditText email = (EditText) findViewById(R.id.emailInput);
        EditText password = (EditText) findViewById(R.id.passwordInput);
        TextView registerText = (TextView) findViewById(R.id.toRegisterActivity);
        errorReportField = (TextView) findViewById(R.id.loginNotifText);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // clear errors on new button press
                errorReportField.setText("");

                String emailText = email.getText().toString();
                String pass = password.getText().toString();
                if(EmailValidator.validate(emailText)){
                    presenter.submitLogin(emailText, pass);
                }
                else{
                    // invalid email
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
        if(o.getClass().getSimpleName().equals("String")){
            errorReportField.setText((String) o);
        }
    }

    public void setPresenter(LoginPresenter presenter){
        this.presenter = presenter;
        presenter.setActivity(this);
    }
}