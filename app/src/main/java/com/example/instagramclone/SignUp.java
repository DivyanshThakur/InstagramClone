package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit, btnGetAllData;
    private EditText edtName, edtPunchPower, edtPunchSpeed, edtKickPower, edtKickSpeed;
    private TextView txtGetData;
    private String Data;

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
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);

        btnSubmit.setOnClickListener(SignUp.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");

                parseQuery.getInBackground("ggZebdw4NE", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(object.get("name") + "\n Punch Power: " + object.get("punch_power") + "\n Punch Speed: " + object.get("punch_speed"));
                        } else Toast.makeText(SignUp.this,"Error",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        btnGetAllData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Data = "";

                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (objects.size() > 0 && e == null) {

                            for (ParseObject kickBoxer : objects) {

                                Data += kickBoxer.get("name") + ", Kick Power: " + kickBoxer.get("kick_power") +"\n";
                                Toast.makeText(SignUp.this,Data,Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                });
            }
        });

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

    }

}
