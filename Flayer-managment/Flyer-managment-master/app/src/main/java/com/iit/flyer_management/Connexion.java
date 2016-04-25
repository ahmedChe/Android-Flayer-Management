package com.iit.flyer_management; /**
 * Created by !l-PazZ0 on 24/04/2016.
 */
import android.app.Application;

import com.parse.Parse;
import com.facebook.FacebookSdk;
public class Connexion  extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("PZ09SFfwmznMxx2EGHgnkk1V5yOg2I6SImQgfO0m")
                .server("https://parseapi.back4app.com")
                .clientKey("Ituv4IRgFvDndajPGJG44vE2jotoCZPWJuKudmtf")
                .build()
        );

    }
}