package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    private static final String CNAME = SplashActivity.class.getCanonicalName();
    private static final String TAG = "SplashActivity";
    private static final String EXTRA_REWATCH = CNAME + ".Rewatch";

    private static final long ANIM_TIME_MS = 1500L;

    private Handler mHandler;

    @BindView(R.id.ivSplash)
    ImageView mSplash;


    public static Intent getRewatchLaunchIntent(final Context context) {
        return new Intent(context, SplashActivity.class)
                .putExtra(EXTRA_REWATCH, true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private void goToLoginActivity() {
        startActivity(LoginActivity.getLaunchIntent(this));
        finish();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        if (intent == null || !intent.getBooleanExtra(EXTRA_REWATCH, false) &&
                CurrentUser.exists() || CurrentUser.shouldBeFetched()) {
            goToLoginActivity();
        }

        setContentView(R.layout.activity_splash);

        mHandler = new Handler();

        final Runnable four = new Runnable() {
            @Override
            public void run() {
                if (!isAlive()) {
                    return;
                }

                if (intent == null || !intent.getBooleanExtra(EXTRA_REWATCH, false)) {
                    goToLoginActivity();
                } else {
                    finish();
                }
            }
        };

        final Runnable three = new Runnable() {
            @Override
            public void run() {
                if (!isAlive()) {
                    return;
                }

                mSplash.setImageResource(R.drawable.three);
                mHandler.postDelayed(four, ANIM_TIME_MS);
            }
        };

        final Runnable two = new Runnable() {
            @Override
            public void run() {
                if (!isAlive()) {
                    return;
                }

                mSplash.setImageResource(R.drawable.two);
                mHandler.postDelayed(three, ANIM_TIME_MS);
            }
        };

        mHandler.postDelayed(two, ANIM_TIME_MS);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        super.onDestroy();
    }

    @Override
    protected boolean showSearchIcon() {
        return false;
    }

}
