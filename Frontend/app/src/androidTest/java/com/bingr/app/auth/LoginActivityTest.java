package com.bingr.app.auth;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.bingr.app.R;
import com.bingr.app.SwipeScreen;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> mIntentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Mock
    public LoginPresenter presenter;

    @Test
    public void loginButton_callsPresenter() throws JSONException {
        String email = "ree@ree.ree";
        String password = "ree";
        presenter = mock(LoginPresenter.class);
        doNothing().when(presenter).submitLogin(email, password);

        LoginActivity activity = mIntentsTestRule.getActivity();
        activity.setPresenter(presenter);

        onView(withId(R.id.emailInput)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        verify(presenter, times(1)).submitLogin(email, password);
    }

    @Test
    public void registerButton_goesTo_registerActivity() {

        onView(withId(R.id.toRegisterActivity)).perform(click());
        intended(hasComponent(RegisterActivity.class.getName()));
    }
}
