package com.ameron32.apps.tapnotes.v2.data.backendless.model;

import com.ameron32.apps.tapnotes.v2.data.model.IProgram;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;

/**
 * Created by klemeilleur on 4/3/16.
 */
public class BProgram extends BObject implements IProgram {

    public BProgram() {
        // required empty constructor
    }

    public BProgram(IProgram program) {
        super();
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
    public String getId() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
