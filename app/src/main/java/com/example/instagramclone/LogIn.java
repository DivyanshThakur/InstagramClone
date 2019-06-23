package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
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
            ParseUser.getCurrentUser().logOut();
        }


        edtEmailLA = findViewById(R.id.edtEmailLA);
        edtPasswordLA = findViewById(R.id.edtPasswordLA);

        btnSignUpLA = findViewById(R.id.btnSignUpLA);
        btnLogInLA = findViewById(R.id.btnLogInLA);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUpLA :
                finish();
                break;
            case R.id.btnLogInLA :

                ParseUser.logInInBackground(edtEmailLA.getText().toString(), edtPasswordLA.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            Toasty.success(LogIn.this, user.getUsername() + " is Logged In successfully", Toast.LENGTH_SHORT, true).show();

                        }  else {
                            Toasty.error(LogIn.this, "There was an Error : " + e.getMessage(), Toast.LENGTH_LONG, true).show();

                        }
                    }
                });

                break;
        }
    }
}
