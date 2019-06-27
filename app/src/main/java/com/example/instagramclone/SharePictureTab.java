package com.example.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imageShare;
    private EditText edtDescription;
    private Button btnShareImage;
    Bitmap receivedImageBitmap;


    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imageShare = view.findViewById(R.id.imageShare);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnShareImage = view.findViewById(R.id.btnShareImage);

        imageShare.setOnClickListener(SharePictureTab.this);
        btnShareImage.setOnClickListener(SharePictureTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageShare:

                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                    getChosenImage();
                }

                break;
            case R.id.btnShareImage:

             if (receivedImageBitmap != null) {

                 if (edtDescription.getText().toString().equals("")) {
                     Toasty.error(getContext(),"Please enter the Description", Toasty.LENGTH_LONG,true).show();
                 } else  {

                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                     receivedImageBitmap.compress(Bitmap.CompressFormat.JPEG,0,byteArrayOutputStream);
                     byte[] bytes = byteArrayOutputStream.toByteArray();


                     ParseFile parseFile = new ParseFile("image.png", bytes);
                     ParseObject parseObject = new ParseObject("Photo");
                     parseObject.put("picture", parseFile);
                     parseObject.put("image_des", edtDescription.getText().toString());
                     parseObject.put("username", ParseUser.getCurrentUser().getUsername());


                     final ProgressDialog dialog = new ProgressDialog(getContext());
                     dialog.setMessage("Loading...");

                     dialog.show();


                     parseObject.saveInBackground(new SaveCallback() {
                         @Override
                         public void done(ParseException e) {
                             if (e == null) {

                                 Toasty.success(getContext(),"Done!!!!", Toasty.LENGTH_SHORT,true).show();

                             } else {
                                 Toasty.error(getContext(),"Unknown Error: " + e.getMessage(), Toasty.LENGTH_LONG,true).show();

                             }
                             dialog.dismiss();
                         }
                     });
                 }

             } else  {
                 Toasty.error(getContext(),"You must select an Image", Toasty.LENGTH_LONG,true).show();
             }

                break;

        }
    }

    private void getChosenImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                // Do something with our captured image.
                try {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imageShare.setImageBitmap(receivedImageBitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }
