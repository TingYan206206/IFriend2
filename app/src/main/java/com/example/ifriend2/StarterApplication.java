package com.example.ifriend2;

/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */


import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);


        //
        // cd apps/parse/htdocs/
        // vi server.js
        // //bitmani parse name: user, key: PlpC3SoVm0Hc


        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("bbc3eb85d31a9be68ac603b7400a6a00fd21ea16")
                .clientKey("5233d10f6f86f3075996bd4b7c4821af83c7aa46")
                .server("http://3.80.200.245:80/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "122");
//        object.put("myString", "zzzzz");
//
//        object.saveInBackground(new SaveCallback () {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed" + ex.toString());
//                }
//            }
//        });

//        ParseObject score = new ParseObject("Score");
//        score.put("username", "nick");
//        score.put("score", 45);
//        score.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    // ok
//                    Log.i("success", "saved");
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

