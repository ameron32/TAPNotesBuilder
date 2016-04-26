package com.ameron32.apps.tapnotes.v2.data.backendless.model;

import com.ameron32.apps.tapnotes.v2.data.model.IUser;
import com.backendless.BackendlessUser;

/**
 * Created by klemeilleur on 4/26/2016.
 */
public class BUser extends BackendlessUser implements IUser {

  @Override
  public String getId() {
    return getObjectId();
  }
}
