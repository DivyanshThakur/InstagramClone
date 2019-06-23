package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;
    private EditText edtName, edtPunchPower, edtPunchSpeed, edtKickPower, edtKickSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSubmit = findViewById(R.id.btnSubmit);
        edtName = findViewById(R.id.edtName);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);

        btnSubmit.setOnClickListener(SignUp.this);

    }

    @Override
    public void onClick(View view) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");


            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punch_power",  Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kick_speed",  Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kick_power",  Integer.parseInt(edtKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(SignUp.this, kickBoxer.get("name").toString()  + " is saved successfully",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
       // ParseUser.getCurrentUser().logOut();
    }

}
