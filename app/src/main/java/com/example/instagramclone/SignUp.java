package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity {

    public TextView txtHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtHelloWorld = findViewById(R.id.txtHelloWorld);


    }

    public void txt(View view) {
        ParseObject boxer = new ParseObject("Boxer");
        boxer.put("punch_speed",200);

        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, "Boxer class saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        } );

       /* ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("punch_speed",100);
        kickBoxer.put("punch_power",150);
        kickBoxer.put("kick_speed",300);
        kickBoxer.put("kick_power",500);

        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) {
                    Toast.makeText(SignUp.this, "KickBoxer class saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}
