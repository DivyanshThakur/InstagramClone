package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import es.dmoral.toasty.Toasty;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLA, edtPasswordLA;
    private Button btnSignUpLA, btnLogInLA;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setTitle("Log In");


        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }


        edtEmailLA = findViewById(R.id.edtEmailLA);
        edtPasswordLA = findViewById(R.id.edtPasswordLA);

        edtPasswordLA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    btnLogInLA.callOnClick();
                }
                return false;
            }
        });

        btnSignUpLA = findViewById(R.id.btnSignUpLA);
        btnLogInLA = findViewById(R.id.btnLogInLA);

        btnSignUpLA.setOnClickListener(this);
        btnLogInLA.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUpLA:
                finish();
                break;
            case R.id.btnLogInLA:
                if (edtEmailLA.getText().toString().equals("") || edtPasswordLA.getText().toString().equals("")) {
                    Toasty.info(LogIn.this, "E-mail, Password is required", Toast.LENGTH_LONG, true).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(LogIn.this);
                    progressDialog.setMessage(" Logging In " + edtEmailLA.getText().toString());
                    progressDialog.show();

                    ParseUser.logInInBackground(edtEmailLA.getText().toString(), edtPasswordLA.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                Toasty.success(LogIn.this, user.getUsername() + " is Logged In successfully", Toast.LENGTH_SHORT, true).show();

                                transitionToSocialMediaActivity();
                            } else {
                                Toasty.error(LogIn.this, "There was an Error : " + e.getMessage(), Toast.LENGTH_LONG, true).show();

                            }
                            progressDialog.dismiss();
                        }
                    });

                    break;
                }
        }
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LogIn.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
