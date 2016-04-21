package com.ameron32.apps.tapnotesbuilder;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.model.INote;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.di.Injector;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
{

  @Inject
  DataManager dataManager;

  @Bind(R.id.text)
  TextView textView;

  Handler networkHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    inject(this);
    ButterKnife.bind(this);
    networkHandler = new Handler();
  }

  void inject(Activity activity) {
    Injector.INSTANCE.init(new ActivityModule(activity));
    Injector.INSTANCE.inject(activity);
  }

  Subscription subscription;

  @Override
  protected void onResume() {
    super.onResume();
    subscription = dataManager.syncPrograms()
        .subscribeOn(HandlerScheduler.from(networkHandler))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(programObserver);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (!subscription.isUnsubscribed()) subscription.unsubscribe();
  }

  Observer<List<IProgram>> programObserver = new Observer<List<IProgram>>() {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
      e.printStackTrace();
    }

    @Override
    public void onNext(List<IProgram> programs) {
      textView.setText("program size " + programs.size());
      for (IProgram program : programs) {
        subscription = dataManager.syncTalks(program)
            .subscribeOn(Schedulers.computation())
            .observeOn(HandlerScheduler.from(networkHandler))
            .subscribe(talkObserver);
      }
    }
  };

  Observer<List<ITalk>> talkObserver = new Observer<List<ITalk>>() {
    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
      e.printStackTrace();
    }

    @Override
    public void onNext(List<ITalk> talks) {
      textView.append("\ntalk size " + talks.size());
    }
  };
}
