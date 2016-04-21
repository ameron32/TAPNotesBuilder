package com.ameron32.apps.tapnotesbuilder;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.di.Injector;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
{

  @Inject
  DataManager dataManager;

  @Bind(R.id.text)
  TextView textView;

  Handler networkHandler;
  CompositeSubscription networkSubscriptions;

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

  @Override
  protected void onResume() {
    super.onResume();
    hold(dataManager.syncPrograms()
        .subscribeOn(HandlerScheduler.from(networkHandler))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(programObserver));
  }

  void hold(Subscription sub) {
    if (networkSubscriptions == null ||
        networkSubscriptions.isUnsubscribed()) {
      networkSubscriptions = new CompositeSubscription();
    }
    networkSubscriptions.add(sub);
  }

  void releaseSubscriptions() {
    if (!networkSubscriptions.isUnsubscribed()) {
      networkSubscriptions.unsubscribe();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    releaseSubscriptions();
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
        hold(dataManager.syncTalks(program)
            .subscribeOn(Schedulers.computation())
            .observeOn(HandlerScheduler.from(networkHandler))
            .subscribe(talkObserver));
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
