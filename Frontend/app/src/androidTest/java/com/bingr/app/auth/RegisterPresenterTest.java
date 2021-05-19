package com.bingr.app.auth;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.bingr.app.SwipeScreen;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterPresenterTest {

    @Rule
    public IntentsTestRule<RegisterActivity> mIntentsTestRule = new IntentsTestRule<>(RegisterActivity.class);

    @Mock
    public AuthRequest requester;

    @Mock
    public RegisterActivity activity;

    @Test
    public void submitRegister_callsRequest() throws JSONException {
        // fill request parameters
        Map<String, String> params = new HashMap<>();
        params.put("name", "lathoa");
        params.put("firstName", "Joe");
        params.put("surname", "Mama");
        params.put("emailId", "ree@ree.ree");
        params.put("password", "testTest3");
        JSONObject userData = new JSONObject(params);

        requester = mock(AuthRequest.class);
        doNothing().when(requester).registerRequest(userData);

        RegisterPresenter presenter = new RegisterPresenter(requester);
        doNothing().when(requester).setPresenter(presenter);

        presenter.submitRegister(userData);

        verify(requester, times(1)).registerRequest(userData);
    }

    @Test
    public void updateSuccess_createsIntent() throws JSONException {

        requester = mock(AuthRequest.class);
        activity = mock(RegisterActivity.class);
        RegisterPresenter presenter = new RegisterPresenter();
        presenter.setActivity(activity);


        doNothing().when(requester).setPresenter(presenter);
        when(activity.getContext()).thenReturn(mIntentsTestRule.getActivity().getContext());


        JSONObject o = new JSONObject();
        o.put("message", "success");
        presenter.update(o);

        intended(hasComponent(SwipeScreen.class.getName()));
    }
}
