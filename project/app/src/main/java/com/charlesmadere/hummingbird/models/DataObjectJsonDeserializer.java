package com.charlesmadere.hummingbird.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DataObjectJsonDeserializer implements JsonDeserializer<DataObject> {

    @Override
    public DataObject deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }

        final JsonObject jsonObject;

        try {
            jsonObject = json.getAsJsonObject();
        } catch (final ClassCastException | UnsupportedOperationException e) {
            throw new JsonParseException("couldn't get json as JsonObject: \"" + json + '"', e);
        }

        final JsonElement dataTypeJson = jsonObject.get("type");

        if (dataTypeJson == null) {
            throw new JsonParseException("dataTypeJson doesn't exist: \"" + json + '"');
        } else if (dataTypeJson.isJsonNull()) {
            throw new JsonParseException("dataTypeJson is null: \"" + json + '"');
        }

        final DataType dataType = context.deserialize(dataTypeJson, DataType.class);
        final DataObject dataObject;

        switch (dataType) {
            case ACTIVITIES:
                dataObject = context.deserialize(json, Action.class);
                break;

            case ACTIVITY_GROUPS:
                dataObject = context.deserialize(json, ActionGroup.class);
                break;

            case ANIME:
                dataObject = context.deserialize(json, AnimeV3.class);
                break;

            case CASTINGS:
                dataObject = context.deserialize(json, Casting.class);
                break;

            case CHARACTERS:
                dataObject = context.deserialize(json, Character.class);
                break;

            case COMMENT_LIKES:
                dataObject = context.deserialize(json, CommentLike.class);
                break;

            case COMMENTS:
                dataObject = context.deserialize(json, Comment.class);
                break;

            case EPISODES:
                dataObject = context.deserialize(json, Episode.class);
                break;

            case FAVORITES:
                dataObject = context.deserialize(json, Favorite.class);
                break;

            case FOLLOWS:
                dataObject = context.deserialize(json, Follow.class);
                break;

            case FRANCHISES:
                dataObject = context.deserialize(json, FranchiseV3.class);
                break;

            case GENRES:
                dataObject = context.deserialize(json, Genre.class);
                break;

            case INSTALLMENTS:
                dataObject = context.deserialize(json, Installment.class);
                break;

            case LIBRARY_ENTRIES:
                dataObject = context.deserialize(json, LibraryEntry.class);
                break;

            case MANGA:
                dataObject = context.deserialize(json, MangaV3.class);
                break;

            case PEOPLE:
                dataObject = context.deserialize(json, Person.class);
                break;

            case POST_LIKES:
                dataObject = context.deserialize(json, PostLike.class);
                break;

            case POSTS:
                dataObject = context.deserialize(json, Post.class);
                break;

            case REVIEWS:
                dataObject = context.deserialize(json, Review.class);
                break;

            case ROLES:
                dataObject = context.deserialize(json, Role.class);
                break;

            case STREAMERS:
                dataObject = context.deserialize(json, Streamer.class);
                break;

            case STREAMING_LINKS:
                dataObject = context.deserialize(json, StreamingLink.class);
                break;

            case USER_ROLES:
                dataObject = context.deserialize(json, UserRole.class);
                break;

            case USERS:
                dataObject = context.deserialize(json, UserV3.class);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        DataObject.class.getSimpleName() + ": \"" + dataTypeJson + '"');
        }

        return dataObject;
    }

}
