package com.ameron32.apps.tapnotes.v2.data;

import com.ameron32.apps.tapnotes.v2.data.model.IObject;

import java.util.List;

import rx.Observable;

/**
 * Created by klemeilleur on 4/12/2016.
 */
public interface SyncEvent {

    void onCreate(DataAccess dataAccess);
    Observable<List<IObject>> performAction();
    void onStopService(DataAccess dataAccess);
}
