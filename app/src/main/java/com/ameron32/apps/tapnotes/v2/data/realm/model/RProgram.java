package com.ameron32.apps.tapnotes.v2.data.realm.model;

import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by klemeilleur on 4/25/2016.
 */
public class RProgram extends RealmObject implements IProgram {

  @Required String objectId;
  String imageUrl;
  String name;

  public RProgram(IProgram iProgram) {
    objectId = iProgram.getId();
    imageUrl = iProgram.getImageUrl();
    name = iProgram.getName();
  }

  @Override
  public ITalk[] getTalks() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getImageUrl() {
    return null;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public void setUserTimestamp(Date date) {

  }
}
