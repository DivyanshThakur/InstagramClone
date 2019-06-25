package com.example.instagramclone;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavSport);

        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null) {
            edtProfileName.setText("");
        } else  {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileBio") == null) {
            edtProfileBio.setText("");
        } else  {
            edtProfileBio.setText(parseUser.get("profileBio").toString());
        }

        if (parseUser.get("profileProfession") == null) {
            edtProfileProfession.setText("");
        } else  {
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());
        }

        if (parseUser.get("profileHobbies") == null) {
            edtProfileHobbies.setText("");
        } else  {
            edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());
        }

        if (parseUser.get("profileFavouriteSport") == null) {
            edtProfileFavSport.setText("");
        } else  {
            edtProfileFavSport.setText(parseUser.get("profileFavouriteSport").toString());
        }

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtProfileName.getText().toString().equals("") || edtProfileBio.getText().toString().equals("") || edtProfileProfession.getText().toString().equals("") || edtProfileHobbies.getText().toString().equals("")  || edtProfileFavSport.getText().toString().equals("")) {
                    Toasty.error(getContext(), "Please Enter all data", Toast.LENGTH_LONG, true).show();

                } else {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("favouriteSport", edtProfileFavSport.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Updating user info");
                    progressDialog.show();

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toasty.info(getContext(), "Updated Info successfully", Toast.LENGTH_SHORT, true).show();

                            } else {
                                Toasty.error(getContext(), e.getMessage(), Toast.LENGTH_LONG, true).show();

                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

        return view;

    }

}
