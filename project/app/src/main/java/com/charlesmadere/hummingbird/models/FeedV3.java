package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Iterator;

public class FeedV3 implements Parcelable {

    private ArrayList<AbsStoryV3> mStories;


    @WorkerThread
    public FeedV3(final ArrayResponse<ActionGroup> response) {
        if (!response.hasData()) {
            return;
        }

        final ArrayList<ActionGroup> actionGroups = response.getData();

        // noinspection ConstantConditions
        final Iterator<ActionGroup> iterator = actionGroups.iterator();

        while (iterator.hasNext()) {
            searchActionGroups(response, iterator);
        }
    }

    private void buildStory(final ArrayResponse<ActionGroup> response,
            final ArrayList<DataObject.Stub> array) {
        final Iterator<DataObject.Stub> iterator = array.iterator();
        final ArrayList<DataObject> included = response.getIncluded();

        ArrayList<Action> actions = null;

        while (iterator.hasNext()) {
            final DataObject.Stub object = iterator.next();
            final String id = object.getId();

            // noinspection ConstantConditions
            for (final DataObject inc : included) {
                if (id.equals(inc.getId())) {

                }
            }
        }

        if (actions != null && !actions.isEmpty()) {

        }
    }

    private void searchActionGroups(final ArrayResponse<ActionGroup> response,
            final Iterator<ActionGroup> iterator) {
        final ActionGroup actionGroup = iterator.next();
        final Relationships relationships = actionGroup.getRelationships();
        final Relationship activities = relationships.getActivities();

        if (activities == null) {
            iterator.remove();
            return;
        }

        final ArrayList<DataObject.Stub> array;

        if (activities.hasArray()) {
            array = activities.getArray();
        } else if (activities.hasObject()) {
            array = new ArrayList<>(1);
            array.add(activities.getObject());
        } else {
            iterator.remove();
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

    }

    public static final Creator<FeedV3> CREATOR = new Creator<FeedV3>() {
        @Override
        public FeedV3 createFromParcel(final Parcel source) {
            final FeedV3 f = new FeedV3();

            return f;
        }

        @Override
        public FeedV3[] newArray(final int size) {
            return new FeedV3[size];
        }
    };

}
