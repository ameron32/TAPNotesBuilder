package com.ameron32.apps.tapnotes.v2.data.realm;

import com.ameron32.apps.tapnotes.v2.data.frmk.LocalHelper;
import com.ameron32.apps.tapnotes.v2.data.model.INote;
import com.ameron32.apps.tapnotes.v2.data.model.IObject;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.data.model.IUser;
import com.ameron32.apps.tapnotes.v2.data.realm.model.RProgram;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by klemeilleur on 4/26/2016.
 */
public class RealmLocalHelper implements LocalHelper {

  private static RealmLocalHelper SINGLETON;

  public static RealmLocalHelper get() {
    if (SINGLETON == null) {
      SINGLETON = new RealmLocalHelper();
    }
    return SINGLETON;
  }

  Realm realm;

  private RealmLocalHelper() {
    realm = Realm.getDefaultInstance();
  }

  @Override
  public Observable<INote> createNote(INote note) {
    return null;
  }

  @Override
  public Observable<INote> updateNote(INote note) {
    return null;
  }

  @Override
  public Observable<INote> deleteNote(INote note) {
    return null;
  }

  @Override
  public Observable<List<IProgram>> getPrograms() {
    return null;
  }

  @Override
  public Observable<List<ITalk>> getTalks(IProgram iProgram) {
    return null;
  }

  @Override
  public Observable<List<INote>> getNotes(IProgram program, ITalk talk, DateTime date, IUser user) {
    return null;
  }

  @Override
  public Observable<IProgram> getProgram(String programId) {
    return realm.where(RProgram.class).equalTo("objectId", programId)
        .findAllAsync().asObservable()
        .flatMap(new Func1<RealmResults<RProgram>, Observable<RProgram>>() {
          @Override
          public Observable<RProgram> call(final RealmResults<RProgram> rPrograms) {return Observable.from(rPrograms);
          }
        })
        .map(new Func1<RProgram, IProgram>() {
          @Override
          public IProgram call(RProgram rProgram) {
            return rProgram;
          }
        });
  }

  @Override
  public Observable<ITalk> getTalk(String talkId) {
    return null;
  }

  @Override
  public Observable<ITalk> getTalkAtSequence(String sequencePosition) {
    return null;
  }

  @Override
  public Observable<INote> getNote(String noteId) {
    return null;
  }

  @Override
  public Observable<List<IProgram>> pinPrograms(List<IProgram> programs) {
    return null;
  }

  @Override
  public Observable<List<ITalk>> pinTalks(List<ITalk> talks) {
    return null;
  }

  @Override
  public Observable<List<INote>> pinNotes(List<INote> notes) {
    return null;
  }

  @Override
  public Observable<IProgram> pinProgram(final IProgram program) {
    return Observable.defer(new Func0<Observable<IProgram>>() {
      @Override
      public Observable<IProgram> call() {
        return Observable.create(new Observable.OnSubscribe<IProgram>() {
          @Override
          public void call(Subscriber<? super IProgram> subscriber) {
            RProgram rProgram = new RProgram(program);
            realm.beginTransaction();
            realm.copyToRealm(rProgram);
            realm.commitTransaction();
            subscriber.onNext(rProgram);
          }
        });
      }
    });
  }

  @Override
  public Observable<ITalk> pinTalk(ITalk talk) {
    return null;
  }

  @Override
  public Observable<INote> pinNote(INote note) {
    return null;
  }

  @Override
  public Observable<List<IObject>> getUnsyncedObjects() {
    return null;
  }
}
