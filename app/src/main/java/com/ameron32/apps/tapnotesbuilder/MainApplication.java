package com.ameron32.apps.tapnotesbuilder;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.widget.Toast;

import com.ameron32.apps.tapnotes.v2.data.parse.model.Note;
import com.ameron32.apps.tapnotes.v2.data.parse.model.Program;
import com.ameron32.apps.tapnotes.v2.data.parse.model.Talk;
import com.ameron32.apps.tapnotes.v2.data.parse.model.User;
import com.ameron32.apps.tapnotes.v2.di.Injector;
import com.backendless.Backendless;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by klemeilleur on 4/21/2016.
 */
public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    inject(this);
    initializeParse(this);
    initializeBackendless(this);
    initializeJodaTimeAndroid(this);
  }

  void inject(Application app) {
//    Injector.INSTANCE.init(new AndroidModule(app));
    Injector.INSTANCE.init(new TAPModule(app));
    Injector.INSTANCE.inject(app);
  }

  void initializeBackendless(Application app) {
    String appVersion = "1";
    try {
      final PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      appVersion = pInfo.versionName + "-" + pInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    Backendless.initApp(this,
        getString(R.string.BACKENDLESS_APPLICATION_ID),
        getString(R.string.BACKENDLESS_ANDROID_SECRET_KEY),
        appVersion);
  }

  void initializeParse(Application app) {
    final Resources r = app.getResources();

    // Register Custom ParseObjects
    ParseObject.registerSubclass(User.class);
    ParseObject.registerSubclass(Program.class);
    ParseObject.registerSubclass(Talk.class);
    ParseObject.registerSubclass(Note.class);
    //

    // Enable Local Data Store
    Parse.enableLocalDatastore(app);

    // Enable Crash Reporting
//    ParseCrashReporting.enable(app);
    Parse.initialize(app,
        r.getString(R.string.APPLICATION_ID),
        r.getString(R.string.CLIENT_KEY));

    // Save the current Installation to Parse.
    ParseInstallation.getCurrentInstallation().saveInBackground(
        new SaveCallback() {
          @Override
          public void done(
              ParseException e) {
installationSaved();
          }
        });
    Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
  }

  void initializeJodaTimeAndroid(Application app) {
    JodaTimeAndroid.init(app);
  }

  void installationSaved() {
    Toast.makeText(this, "Installation saved.", Toast.LENGTH_SHORT).show();
  }
}
