package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {

    private EditText edtEmailSA, edtUsernameSA, edtPasswordSA;
    private Button btnSignUpSA, btnLogInSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }


        edtEmailSA = findViewById(R.id.edtEmailSA);
        edtUsernameSA = findViewById(R.id.edtUsernameSA);
        edtPasswordSA = findViewById(R.id.edtPasswordSA);

        edtPasswordSA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    btnSignUpSA.callOnClick();
                }
                return false;
            }
        });

        btnSignUpSA = findViewById(R.id.btnSignUpSA);
        btnLogInSA = findViewById(R.id.btnLogInSA);


        btnSignUpSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ParseUser parseUser = new ParseUser();
                parseUser.setEmail(edtEmailSA.getText().toString());
                parseUser.setUsername(edtUsernameSA.getText().toString());
                parseUser.setPassword(edtPasswordSA.getText().toString());

                if (edtEmailSA.getText().toString().equals("") || edtUsernameSA.getText().toString().equals("") || edtPasswordSA.getText().toString().equals("")) {
                    Toasty.info(SignUp.this, "E-mail, Username, Password is required", Toast.LENGTH_LONG, true).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage(" Signing Up " + edtUsernameSA.getText().toString());
                    progressDialog.show();

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Toasty.success(SignUp.this, parseUser.getUsername() + " is Signed Up successfully", Toast.LENGTH_SHORT, true).show();

                                transitionToSocialMediaActivity();
                            } else {

                                Toasty.error(SignUp.this, "There was an Error : " + e.getMessage(), Toast.LENGTH_LONG, true).show();

                            }
                            progressDialog.dismiss();
                        }
                    });


                }
            }
        });


        btnLogInSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public void rootLayoutTapped (View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}