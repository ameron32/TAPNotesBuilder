package com.ameron32.apps.tapnotesbuilder;

import android.app.Application;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.SyncEvent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by klemeilleur on 4/21/2016.
 */
@Module(
    injects = {
        MainApplication.class
    },
    addsTo = AndroidModule.class,
    library = true
)
public class TAPModule {

  private final Application application;

  public TAPModule(final Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  DataManager provideDataManager() {
    return new DataManager(
//        DataManager.RemoteSource.Parse,
//        DataManager.LocalSource.ParseOffline,
//        DataManager.SyncEventType.New
    );
  }
}
