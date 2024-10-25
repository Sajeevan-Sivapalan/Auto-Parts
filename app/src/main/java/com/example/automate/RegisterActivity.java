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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignup;
    private TextView txtLogin;
    private ImageView pwdVisible;
    private ImageView pwdConfirmVisible;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnSignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.text_login);
        pwdVisible = findViewById(R.id.imgPasswordVisibility);
        pwdConfirmVisible = findViewById(R.id.imgConfirmPasswordVisibility);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);

        // Set OnClickListener for Button to load Home Activity
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Underline "Login" text
        String registerString = "Login";
        SpannableString mSpannableString = new SpannableString(registerString);
        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
        txtLogin.setText(mSpannableString);

        // Toggle password visibility
        pwdVisible.setOnClickListener(v -> togglePasswordVisibility(edtPassword, pwdVisible));
        pwdConfirmVisible.setOnClickListener(v -> togglePasswordVisibility(edtConfirmPassword, pwdConfirmVisible));

        // Handle login text click
        txtLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.visibility_off);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.visibility);
        }
        // Move the cursor to the end of the text
        editText.setSelection(editText.getText().length());
    }
}