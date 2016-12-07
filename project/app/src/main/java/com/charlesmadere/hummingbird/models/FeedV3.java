package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;

public class FeedV3 implements Parcelable {

    @Nullable
    private ArrayList<AbsStoryV3> mStories;


    private FeedV3() {

    }

    @WorkerThread
    public FeedV3(final ArrayResponse<ActionGroup> response) {
        if (!response.hasData()) {
            return;
        }

        final ArrayList<ActionGroup> actionGroups = response.getData();

        // noinspection ConstantConditions
        for (final ActionGroup actionGroup : actionGroups) {
            searchActionGroups(response, actionGroup);
        }
    }

    private void buildStory(final ArrayResponse<ActionGroup> response,
            final ArrayList<DataObject.Stub> array) {
        final ArrayList<DataObject> included = response.getIncluded();

        ArrayList<Action> actions = null;

        for (final DataObject.Stub object : array) {
            final DataType dataType = object.getDataType();
            final String id = object.getId();

            // noinspection ConstantConditions
            for (final DataObject inc : included) {
                if (dataType == inc.getDataType() && id.equals(inc.getId())) {
                    if (actions == null) {
                        actions = new ArrayList<>();
                    }

                    actions.add((Action) inc);
                }
            }
        }

        if (actions == null || actions.isEmpty()) {
            return;
        }

        actions.get(0).getVerb();
        // TODO convert actions into a story
    }

    private void searchActionGroups(final ArrayResponse<ActionGroup> response,
            final ActionGroup actionGroup) {
        final Relationships relationships = actionGroup.getRelationships();
        final Relationship activities = relationships.getActivities();

        if (activities == null) {
            return;
        }

        final ArrayList<DataObject.Stub> array;

        if (activities.hasArray()) {
            array = activities.getArray();
        } else if (activities.hasObject()) {
            array = new ArrayList<>(1);
            array.add(activities.getObject());
        } else {
            return;
        }

        buildStory(response, array);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<FeedV3> CREATOR = new Creator<FeedV3>() {
        @Override
        public FeedV3 createFromParcel(final Parcel source) {
            final FeedV3 f = new FeedV3();
            // TODO
            return f;
        }

        @Override
        public FeedV3[] newArray(final int size) {
            return new FeedV3[size];
        }
    };

}
