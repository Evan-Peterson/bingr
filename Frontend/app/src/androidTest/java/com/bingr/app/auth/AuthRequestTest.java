package com.bingr.app.auth;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.bingr.app.R;
import com.bingr.app.ResponseObserver;
import com.bingr.app.SwipeScreen;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AuthRequestTest {

    @Mock
    public LoginPresenter loginPresenter;

    @Mock
    public RegisterPresenter registerPresenter;

    public final MockListener mockListener = new MockListener();

    @Test
    public void loginRequest_callsUpdate() throws JSONException, InterruptedException {
        String email = "ree@ree.ree";
        String password = "ree";
        JSONObject response = new JSONObject();
        response.put("message", "success");

        loginPresenter = mock(LoginPresenter.class);
        doNothing().when(loginPresenter).update(response);

        AuthRequest requester = new AuthRequest();
        requester.setPresenter(loginPresenter);
        requester.setPresenter(mockListener);

        requester.loginRequest(email, password);

        synchronized (mockListener){
            mockListener.wait(2000);
        }

        verify(loginPresenter, times(1)).update(any());
    }

    @Test
    public void registerRequest_callsUpdate() throws JSONException, InterruptedException {
        // fill request parameters
        Map<String, String> params = new HashMap<>();
        params.put("name", "lathoa");
        params.put("firstName", "Joe");
        params.put("surname", "Mama");
        params.put("emailId", "ree@ree.ree");
        params.put("password", "testTest3");
        JSONObject userData = new JSONObject(params);

        registerPresenter = mock(RegisterPresenter.class);
        doNothing().when(registerPresenter).update(any());

        AuthRequest requester = new AuthRequest();
        requester.setPresenter(registerPresenter);
        requester.setPresenter(mockListener);

        requester.registerRequest(userData);

        synchronized (mockListener){
            mockListener.wait(2000);
        }

        verify(registerPresenter, times(1)).update(any());
    }
}

class MockListener implements ResponseObserver {

    @Override
    public void update(Object o) {
        synchronized(this){
            notifyAll();
        }
    }
}
