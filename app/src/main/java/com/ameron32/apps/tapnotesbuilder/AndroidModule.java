package com.ameron32.apps.tapnotesbuilder;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.ameron32.apps.tapnotes.v2.di.ForApplication;

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
    library = true
)
public class AndroidModule {

  private final Application application;

  public AndroidModule(final Application application) {
    this.application = application;
  }

  // ==========================================================================================================================
  // Global Android Resources
  // ==========================================================================================================================

  @Provides
  @Singleton
  Application provideApplication() {
    return application;
  }

  @Provides
  @Singleton
  @ForApplication
  Context provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  @ForApplication
  Resources provideResources() {
    return application.getResources();
  }

  // ==========================================================================================================================
  // Persistence
  // ==========================================================================================================================

  @Provides
  @Singleton
  @ForApplication
  SharedPreferences provideSharedPreferences(final Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Provides
  @Singleton
  AssetManager provideAssetManager(final Application application) {
    return application.getAssets();
  }

  // ==========================================================================================================================
  // Android Services
  // ==========================================================================================================================

  @Provides
  @Singleton
  AccountManager provideAccountManager(final Application application) {
    return getSystemService(application, Context.ACCOUNT_SERVICE);
  }

  @Provides
  @Singleton
  ActivityManager provideActivityManager(final Application application) {
    return getSystemService(application, Context.ACTIVITY_SERVICE);
  }

  @Provides
  @Singleton
  AlarmManager provideAlarmManager(final Application application) {
    return getSystemService(application, Context.ALARM_SERVICE);
  }

  @Provides
  @Singleton
  AudioManager provideAudioManager(final Application application) {
    return getSystemService(application, Context.AUDIO_SERVICE);
  }

  @Provides
  @Singleton
  ClipboardManager provideClipboardManager(final Application application) {
    return getSystemService(application, Context.CLIPBOARD_SERVICE);
  }

  @Provides
  @Singleton
  ConnectivityManager provideConnectivityManager(final Application application) {
    return getSystemService(application, Context.CONNECTIVITY_SERVICE);
  }

  @Provides
  @Singleton
  DownloadManager provideDownloadManager(final Application application) {
    return getSystemService(application, Context.DOWNLOAD_SERVICE);
  }

  @Provides
  @Singleton
  InputManager provideInputManager(final Application application) {
    return getSystemService(application, Context.INPUT_SERVICE);
  }

  @Provides
  @Singleton
  LocationManager provideLocationManager(final Application application) {
    return getSystemService(application, Context.LOCATION_SERVICE);
  }

  @Provides
  @Singleton
  NfcManager provideNfcManager(final Application application) {
    return getSystemService(application, Context.NFC_SERVICE);
  }

  @Provides
  @Singleton
  NotificationManager provideNotificationManager(final Application application) {
    return getSystemService(application, Context.NOTIFICATION_SERVICE);
  }

  @Provides
  @Singleton
  PowerManager providePowerManager(final Application application) {
    return getSystemService(application, Context.POWER_SERVICE);
  }

  @Provides
  @Singleton
  SearchManager provideSearchManager(final Application application) {
    return getSystemService(application, Context.SEARCH_SERVICE);
  }

  @Provides
  @Singleton
  SensorManager provideSensorManager(final Application application) {
    return getSystemService(application, Context.SENSOR_SERVICE);
  }

  @Provides
  @Singleton
  StorageManager provideStorageManager(final Application application) {
    return getSystemService(application, Context.STORAGE_SERVICE);
  }

  @Provides
  @Singleton
  TelephonyManager provideTelephonyManager(final Application application) {
    return getSystemService(application, Context.TELEPHONY_SERVICE);
  }

  @Provides
  @Singleton
  UsbManager provideUsbManager(final Application application) {
    return getSystemService(application, Context.USB_SERVICE);
  }

  @Provides
  @Singleton
  Vibrator provideVibrator(final Application application) {
    return getSystemService(application, Context.VIBRATOR_SERVICE);
  }

  @Provides
  @Singleton
  WifiManager provideWifiManager(final Application application) {
    return getSystemService(application, Context.WIFI_SERVICE);
  }

  private <T> T getSystemService(final Application application, final String service) {
    //noinspection unchecked
    return (T) application.getSystemService(service);
  }
}
