package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;

import java.lang.ref.WeakReference;

public class AnimeActivity extends BaseDrawerActivity {

    private static final String CNAME = AnimeActivity.class.getCanonicalName();
    private static final String TAG = "AnimeActivity";
    private static final String EXTRA_ANIME = CNAME + ".Anime";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AbsAnime mAnime;
    private AnimeV2 mAnimeV2;


    public static Intent getLaunchIntent(final Context context, final AbsAnime anime) {
        return new Intent(context, AnimeActivity.class)
                .putExtra(EXTRA_ANIME, anime);
    }

    private void fetchAnimeV2() {
        // TODO
        Api.getAnimeById(mAnime, new GetAnimeByIdListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        final Intent intent = getIntent();
        mAnime = intent.getParcelableExtra(EXTRA_ANIME);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mAnimeV2 = savedInstanceState.getParcelable(KEY_ANIME_V2);
        }

        if (mAnimeV2 == null) {
            switch (mAnime.getVersion()) {
                case V1:
                    fetchAnimeV2();
                    break;

                case V2:
                    showAnimeV2((AnimeV2) mAnime);
                    break;

                default:
                    throw new RuntimeException("encountered illegal " +
                            AbsAnime.Version.class.getName() + ": " + mAnime.getVersion());
            }
        } else {
            showAnimeV2(mAnimeV2);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAnimeV2 != null) {
            outState.putParcelable(KEY_ANIME_V2, mAnimeV2);
        }
    }

    private void showAnimeV2(final AnimeV2 animeV2) {
        mAnimeV2 = animeV2;
        // TODO
    }

    private void showError() {
        // TODO
    }


    private static class GetAnimeByIdListener implements ApiResponse<AnimeV2> {
        private final WeakReference<AnimeActivity> mActivityReference;

        private GetAnimeByIdListener(final AnimeActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(@Nullable final AnimeV2 animeV2) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAnimeV2(animeV2);
            }
        }
    }

}
