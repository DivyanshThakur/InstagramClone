package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUPLogIn extends AppCompatActivity {

    private EditText edtUserNameSignUp, edtUserNameLogIn, edtPasswordSignUp, edtPasswordLogIn;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login);

        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtUserNameLogIn = findViewById(R.id.edtUserNameLogIn);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignUPLogIn.this, appUser.getUsername() + " is Signed Up successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUPLogIn.this, e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser.logInInBackground(edtUserNameLogIn.getText().toString(), edtPasswordLogIn.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            Toast.makeText(SignUPLogIn.this, user.getUsername() + " is Logged In successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUPLogIn.this, e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}
