package com.bingr.app.auth;

import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.bingr.app.SwipeScreen;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static androidx.test.espresso.intent.Intents.getIntents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

    @Rule
    public IntentsTestRule<LoginActivity> mIntentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Mock
    public AuthRequest requester;

    @Mock
    public LoginActivity activity;

    @Test
    public void submitLogin_callsRequest(){
        String email = "ree@ree.com";
        String password = "ree";

        requester = mock(AuthRequest.class);
        doNothing().when(requester).loginRequest(email, password);

        LoginPresenter presenter = new LoginPresenter(requester);
        doNothing().when(requester).setPresenter(presenter);

        presenter.submitLogin(email, password);

        verify(requester, times(1)).loginRequest(email, password);
    }

    @Test
    public void updateSuccess_createsIntent() throws JSONException {

        requester = mock(AuthRequest.class);
        activity = mock(LoginActivity.class);
        LoginPresenter presenter = new LoginPresenter();
        presenter.setActivity(activity);


        doNothing().when(requester).setPresenter(presenter);
        when(activity.getContext()).thenReturn(mIntentsTestRule.getActivity().getContext());


        JSONObject o = new JSONObject();
        o.put("message", "success");
        presenter.update(o);

        intended(hasComponent(SwipeScreen.class.getName()));
    }
}
