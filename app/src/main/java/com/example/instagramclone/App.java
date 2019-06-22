package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("vsELI3EfjnSicTIulFGXCjat9zp28OumEptv0qS2")
                // if defined
                .clientKey("hqqdHjLJUWZbMyZJRNPJIozBV66ZTH9cDIHGe7iD")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
