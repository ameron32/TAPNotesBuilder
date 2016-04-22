package com.ameron32.apps.tapnotesbuilder.ui.base;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by klemeilleur on 4/21/2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

  private final DataManager dataManager;
  private Subscription subscription;

  public MainPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(MainMvpView mvpView) {
    super.attachView(mvpView);
  }

  @Override
  public void detachView() {
    super.detachView();
    if (subscription != null) subscription.unsubscribe();
  }

  public void loadTalks(String programId) {
    checkViewAttached();
    subscription =
        dataManager.getProgram(programId)
        .flatMap(new Func1<IProgram, Observable<List<ITalk>>() {
          @Override
          public Observable<List<ITalk>> call(IProgram program) {
            return dataManager.getTalks(program);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Subscriber<List<ITalk>>() {

          @Override
          public void onCompleted() {}

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().showTalksError();
          }

          @Override
          public void onNext(List<ITalk> talks) {
            if (talks == null || talks.isEmpty()) {
              getMvpView().showTalksError();
            } else {
              getMvpView().showTalks(talks);
            }
          }
        });
  }
}
