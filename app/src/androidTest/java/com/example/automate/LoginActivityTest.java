package com.example.automate;

import static org.junit.Assert.*;

import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private EditText edtPassword;
    private ImageView pwdVisible;

    @Before
    public void setUp() {
        // Launch the activity using ActivityScenario
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            scenario.onActivity(activity -> {
                edtPassword = activity.findViewById(R.id.edt_password);
                pwdVisible = activity.findViewById(R.id.imgPasswordVisibility);
            });
        }
    }

    @Test
    public void testTogglePasswordVisibility_ShowPassword() {
        // Initially password should be hidden
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // Simulate visibility toggle
        pwdVisible.performClick();

        // Check if password visibility is changed to visible
        assertTrue(edtPassword.getTransformationMethod() instanceof HideReturnsTransformationMethod);
    }

    @Test
    public void testTogglePasswordVisibility_HidePassword() {
        // Initially show the password
        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        // Simulate visibility toggle
        pwdVisible.performClick();

        // Check if password visibility is hidden
        assertTrue(edtPassword.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    @Test
    public void testLoginButtonClickWithoutCredentials() {
        // Launch the activity
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            // Click the login button
            scenario.onActivity(activity -> {
                Button btnLogin = activity.findViewById(R.id.btnLogin);
                btnLogin.performClick();
            });
        }
    }

    @Test
    public void testSignupTextClick() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            scenario.onActivity(activity -> {
                TextView txtSignup = activity.findViewById(R.id.text_register);
                txtSignup.performClick();
            });
        }
    }

    @Test
    public void testCorrectPasswordEntry() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            scenario.onActivity(activity -> {
                // Simulate entering a correct password
                edtPassword.setText("correct_password");
                Button btnLogin = activity.findViewById(R.id.btnLogin);
                btnLogin.performClick();
            });
        }
    }

}
