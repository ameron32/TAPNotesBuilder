package com.ameron32.apps.tapnotesbuilder;

import android.app.Activity;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.model.INote;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.data.model.IUser;
import com.ameron32.apps.tapnotes.v2.di.Injector;
import com.ameron32.apps.tapnotesbuilder.ui.base.MainMvpView;
import com.ameron32.apps.tapnotesbuilder.ui.base.MainPresenter;
import com.ameron32.apps.tapnotesbuilder.ui.base.Presenter;
import com.fivehundredpx.android.blur.BlurringView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.developersofcydonia.freedtouch.FreeDTouch;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity
    extends AppCompatActivity
    implements MainMvpView
{

  @Inject
  DataManager dataManager;

  @Bind(R.id.text)
  TextView textView;
  @Bind(R.id.button)
  Button button;
  @Bind(R.id.container)
  FrameLayout container;
  @Bind(R.id.blurringView)
  BlurringView blurringView;
  @Bind(R.id.blurrableView)
  View blurrableView;

  MainPresenter mainPresenter;

//  CompositeSubscription networkSubscriptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    inject(this);
    ButterKnife.bind(this);

    mainPresenter = new MainPresenter(dataManager);
    mainPresenter.attachView(this);
    mainPresenter.sync("BPCRNbT6Lf");
//    initFreeDTouchAndBlurringView();
  }

  @Override
  protected void onDestroy() {
    mainPresenter.detachView();
    super.onDestroy();
  }

  void inject(Activity activity) {
    Injector.INSTANCE.init(new ActivityModule(activity));
    Injector.INSTANCE.inject(activity);
  }

  @Override
  public void syncComplete() {
    mainPresenter.loadTalks("BPCRNbT6Lf");
  }

  @Override
  public void showTalks(List<ITalk> talks) {
    textView.setText("Talks: " + talks.size());
    for (ITalk talk :
        talks) {
      textView.append("\n");
      textView.append(talk.getTalkTitle());
    }
  }

  @Override
  public void showTalksEmpty() {
    textView.setText("Talks empty");
  }

  @Override
  public void showTalksError() {

//    textView.setText("Talks error");
  }

  private void initFreeDTouchAndBlurringView() {
    //
//    final String TAG = MainActivity.class.getSimpleName();
//
//    FreeDTouch.OnForceTouchListener onForceTouchListener = new FreeDTouch.OnForceTouchListener() {
//      @Override
//      public void onPeek(View popup, View v, MotionEvent e) {
//        Log.d(TAG, "onPeek");
//        Toast.makeText(MainActivity.this, "PEEK", Toast.LENGTH_SHORT).show();
//        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(20);
//
//        // To find views inside your popup, use popup.findViewById(...).
//        // E.g. -> TextView textView = (TextView) popup.findViewById(R.id.textview);
//      }
//
//      @Override
//      public void onPop(View popup, View v, MotionEvent e) {
//        Log.d(TAG, "onPop");
//        Toast.makeText(MainActivity.this, "POP", Toast.LENGTH_SHORT).show();
//        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(20);
//
//        blurringView.setBlurredView(blurrableView);
//        blurringView.invalidate();
//      }
//
//      @Override
//      public void onClick(View popup, View v, MotionEvent e) {
//        Log.d(TAG, "onClick");
//        Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
//      }
//
//      @Override
//      public void onCancel(View popup, View v, MotionEvent e) {
//        Log.d(TAG, "onCancel");
//        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//      }
//    };
//
//    FreeDTouch.setup(button, onForceTouchListener)
//        .addPopup(container, R.layout.example)
//        .start();
  }

//  @Override
//  protected void onResume() {
//    super.onResume();
//    hold(dataManager.login(X, Y)
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(userObserver));
//  }
//  void hold(Subscription sub) {
//    if (networkSubscriptions == null ||
//        networkSubscriptions.isUnsubscribed()) {
//      networkSubscriptions = new CompositeSubscription();
//    }
//    networkSubscriptions.add(sub);
//  }
//
//  void releaseSubscriptions() {
//    if (!networkSubscriptions.isUnsubscribed()) {
//      networkSubscriptions.unsubscribe();
//    }
//  }
//
//  @Override
//  protected void onPause() {
//    super.onPause();
//    releaseSubscriptions();
//  }
//
//  Observer<IUser> userObserver = new Observer<IUser>() {
//    @Override
//    public void onCompleted() {
//    }
//
//    @Override
//    public void onError(Throwable e) {
//      e.printStackTrace();
//    }
//
//    @Override
//    public void onNext(IUser user) {
//      textView.setText("logged in as: " + user.getId());
//      hold(dataManager.syncPrograms()
//              .subscribeOn(Schedulers.computation())
//              .observeOn(AndroidSchedulers.mainThread())
//              .subscribe(programObserver));
//    }
//  };
//
//  Observer<List<IProgram>> programObserver = new Observer<List<IProgram>>() {
//    @Override
//    public void onCompleted() {
//    }
//
//    @Override
//    public void onError(Throwable e) {
//      e.printStackTrace();
//    }
//
//    @Override
//    public void onNext(List<IProgram> programs) {
//      textView.append("program size " + programs.size());
//      for (IProgram program : programs) {
//        hold(dataManager.syncTalks(program)
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(talkObserver));
//      }
//    }
//  };
//
//  Observer<List<ITalk>> talkObserver = new Observer<List<ITalk>>() {
//    @Override
//    public void onCompleted() {}
//
//    @Override
//    public void onError(Throwable e) {
//      e.printStackTrace();
//    }
//
//    @Override
//    public void onNext(List<ITalk> talks) {
//      textView.append("\ntalk size " + talks.size());
//      byte count = 0;
//      for (ITalk talk : talks) {
//        if (count < 5) {
//          hold(dataManager.syncNotes(talk)
//                  .subscribeOn(Schedulers.computation())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(noteObserver));
//        }
//        count++;
//      }
//    }
//  };
//
//  private Observer<List<INote>> noteObserver = new Observer<List<INote>>() {
//    @Override
//    public void onCompleted() {}
//
//    @Override
//    public void onError(Throwable e) {
//      e.printStackTrace();
//    }
//
//    @Override
//    public void onNext(List<INote> notes) {
//      textView.append("\nnote size " + notes.size());
//    }
//  };
}
