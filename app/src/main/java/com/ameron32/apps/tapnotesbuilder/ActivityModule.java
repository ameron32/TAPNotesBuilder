package com.ameron32.apps.tapnotesbuilder;

import android.app.Activity;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by klemeilleur on 4/21/2016.
 */
@Module(
    injects = {
        MainActivity.class
    },
    library = true
)
public class ActivityModule {

    public ActivityModule(Activity activity) {
    }
}
