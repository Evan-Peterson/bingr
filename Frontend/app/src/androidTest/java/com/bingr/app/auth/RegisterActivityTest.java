package com.bingr.app.auth;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.bingr.app.R;
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
public class RegisterActivityTest {

    @Rule
    public IntentsTestRule<RegisterActivity> mIntentsTestRule = new IntentsTestRule<>(RegisterActivity.class);

    @Mock
    public RegisterPresenter presenter;

    @Test
    public void registerButton_callsPresenter() throws JSONException {
        // fill request parameters
        Map<String, String> params = new HashMap<>();
        params.put("name", "lathoa");
        params.put("firstName", "Joe");
        params.put("surname", "Mama");
        params.put("emailId", "ree@ree.ree");
        params.put("password", "testTest3");
        JSONObject userData = new JSONObject(params);

        presenter = mock(RegisterPresenter.class);
        doNothing().when(presenter).submitRegister(userData);

        RegisterActivity activity = mIntentsTestRule.getActivity();
        activity.setPresenter(presenter);

        onView(withId(R.id.registerUsername)).perform(typeText(userData.getString("name")), closeSoftKeyboard());
        onView(withId(R.id.registerName)).perform(typeText(userData.getString("firstName")), closeSoftKeyboard());
        onView(withId(R.id.registerSurname)).perform(typeText(userData.getString("surname")), closeSoftKeyboard());
        onView(withId(R.id.registerEmail)).perform(typeText(userData.getString("emailId")), closeSoftKeyboard());
        onView(withId(R.id.registerPassword)).perform(typeText(userData.getString("password")), closeSoftKeyboard());
        onView(withId(R.id.registerConfirmPassword)).perform(typeText(userData.getString("password")), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());

        verify(presenter, times(1)).submitRegister(any());
    }
}
