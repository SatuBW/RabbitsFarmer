package com.task.satu.rabbitsfarmer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;

/**
 * Created by Satu on 2016-02-18.
 */
public class RabbitsFarmer extends Application {
    private static RabbitsFarmer singleton;
    public RabbitsFarmer getInstance(){
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(this);


        //  ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
    }
}
