package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.charlesmadere.hummingbird.R;

import butterknife.Bind;

public class SplashActivity extends BaseActivity {

    private static final String CNAME = SplashActivity.class.getCanonicalName();
    private static final String TAG = "SplashActivity";
    private static final String EXTRA_FINISH = CNAME + ".Finish";

    private static final long ANIM_TIME_MS = 1500L;


    private Handler mHandler;

    @Bind(R.id.ivSplash)
    ImageView mSplash;

    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, SplashActivity.class)
                .putExtra(EXTRA_FINISH, true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();

        final Runnable four = new Runnable() {
            @Override
            public void run() {
                if (isDestroyed()) {
                    return;
                }

                final Intent intent = getIntent();

                if (intent == null || !intent.getBooleanExtra(EXTRA_FINISH, false)) {
                    startActivity(LoginActivity.getLaunchIntent(SplashActivity.this));
                }

                finish();
            }
        };

        final Runnable three = new Runnable() {
            @Override
            public void run() {
                if (isDestroyed()) {
                    return;
                }

                mSplash.setImageResource(R.drawable.three);
                mHandler.postDelayed(four, ANIM_TIME_MS);
            }
        };

        final Runnable two = new Runnable() {
            @Override
            public void run() {
                if (isDestroyed()) {
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

}
