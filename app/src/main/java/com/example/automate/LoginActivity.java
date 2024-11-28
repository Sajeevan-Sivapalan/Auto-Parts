package com.example.automate;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtSignup;
    private ImageView pwdVisible;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        pwdVisible = findViewById(R.id.imgPasswordVisibility);
        txtSignup = findViewById(R.id.text_register);
        edtUsername = findViewById(R.id.edt_email);  // Username field
        edtPassword = findViewById(R.id.edt_password);  // Password field

        // Set OnClickListener for Button to validate inputs and load Home Activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Underline "Register" text
        String registerString = "Register";
        SpannableString mSpannableString = new SpannableString(registerString);
        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
        txtSignup.setText(mSpannableString);

        // Toggle password visibility
        pwdVisible.setOnClickListener(v -> togglePasswordVisibility());

        // Handle sign-up text click
        txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Function to validate inputs
    private boolean validateInputs() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Function to toggle password visibility
    private void togglePasswordVisibility() {
        if (edtPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pwdVisible.setImageResource(R.drawable.visibility_off);
        } else {
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pwdVisible.setImageResource(R.drawable.visibility);
        }
        // Move the cursor to the end of the text
        edtPassword.setSelection(edtPassword.getText().length());
    }
}
