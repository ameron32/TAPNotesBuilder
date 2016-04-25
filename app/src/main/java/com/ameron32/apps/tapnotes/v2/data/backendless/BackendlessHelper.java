package com.ameron32.apps.tapnotes.v2.data.backendless;

import com.ameron32.apps.tapnotes.v2.data.DataManager;
import com.ameron32.apps.tapnotes.v2.data.backendless.model.BProgram;
import com.ameron32.apps.tapnotes.v2.data.backendless.model.BTalk;
import com.ameron32.apps.tapnotes.v2.data.frmk.LocalHelper;
import com.ameron32.apps.tapnotes.v2.data.frmk.RemoteHelper;
import com.ameron32.apps.tapnotes.v2.data.frmk.UserHelper;
import com.ameron32.apps.tapnotes.v2.data.model.INote;
import com.ameron32.apps.tapnotes.v2.data.model.IObject;
import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.data.model.IUser;
import com.ameron32.apps.tapnotes.v2.data.realm.model.RProgram;
import com.backendless.Backendless;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by klemeilleur on 3/24/2016.
 */
public class BackendlessHelper {

    LocalHelper cache;
    RemoteHelper remote;
    UserHelper users;

    public BackendlessHelper() {
        cache = new RealmLocalHelper();
        remote = new BackendlessRemoteHelper();
        users = new BackendlessUserHelper();
    }

    class BackendlessRemoteHelper implements RemoteHelper {

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
        public Observable<List<IObject>> getObjects() {
            return null;
        }

        @Override
        public Observable<List<IProgram>> getPrograms() {
            return null;
        }

        @Override
        public Observable<List<ITalk>> getTalks(final IProgram program) {
            return Observable.defer(new Func0<Observable<List<ITalk>>>() {
                @Override
                public Observable<List<ITalk>> call() {
                    return Observable.create(new Observable.OnSubscribe<List<ITalk>>() {
                        @Override
                        public void call(Subscriber<? super List<ITalk>> subscriber) {
                            if (subscriber.isUnsubscribed()) return;
                            String whereClause = "Talk[oProgram].objectId='" + program.getId() + "'";
                            String sortBy = "";
                            String related = "oProgram";
                            final BackendlessDataQuery query = new BackendlessDataQuery();
                            final QueryOptions options = new QueryOptions(100, 0, sortBy);
                            options.addRelated(related);
                            query.setQueryOptions(options);
                            query.setWhereClause(whereClause);
                            List<BTalk> bTalks = Backendless.Data.of(BTalk.class)
                                .find(query).getCurrentPage();
                            List<ITalk> iTalks = new ArrayList<ITalk>();
                            iTalks.addAll(bTalks);
                            subscriber.onNext(iTalks);
                        }
                    });
                }
            });
        }

        @Override
        public Observable<List<INote>> getNotes(IProgram program) {
            return null;
        }

        @Override
        public Observable<List<INote>> getNotes(ITalk talk) {
            return null;
        }

        @Override
        public Observable<IObject> getObject(String objectId) {
            return null;
        }

        @Override
        public Observable<IProgram> getProgram(final String programId) {
            return Observable.defer(new Func0<Observable<IProgram>>() {
                @Override
                public Observable<IProgram> call() {
                    return Observable.create(new Observable.OnSubscribe<IProgram>() {
                        @Override
                        public void call(Subscriber<? super IProgram> subscriber) {
                            if (subscriber.isUnsubscribed()) return;
                            BProgram bProgram = Backendless.Data.of(BProgram.class)
                                .findById(programId);
                            subscriber.onNext(bProgram);
                        }
                    });
                }
            });
        }

        @Override
        public Observable<ITalk> getTalk(String talkId) {
            return null;
        }

        @Override
        public Observable<ITalk> getTalkAtSequence(String sequence) {
            return null;
        }

        @Override
        public Observable<INote> getNote(String noteId) {
            return null;
        }

        @Override
        public Observable<List<INote>> saveNotes(List<INote> iNotes) {
            return null;
        }
    }

    class RealmLocalHelper implements LocalHelper {

        Realm realm;

        public RealmLocalHelper() {
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

    class BackendlessUserHelper implements UserHelper {

    }
}
