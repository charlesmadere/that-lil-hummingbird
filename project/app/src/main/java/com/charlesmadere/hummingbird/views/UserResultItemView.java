package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserResultItemView extends CardView implements AdapterView<SearchBundle.UserResult>,
        View.OnClickListener {

    private SearchBundle.UserResult mUserResult;

    @BindView(R.id.sdvImage)
    SimpleDraweeView mImage;

    @BindView(R.id.tvBio)
    TextView mBio;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.divider)
    View mDivider;


    public UserResultItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public UserResultItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void fetchImages(final SearchBundle.UserResult userResult, final String[] images,
            final int index) {
        if (mUserResult != userResult) {
            return;
        } else if (index >= images.length) {
            mImage.setImageURI((String) null);
            return;
        }

        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        super.onFailure(id, throwable);
                        fetchImages(userResult, images, index + 1);
                    }
                })
                .setOldController(mImage.getController())
                .setUri(images[index])
                .build();

        mImage.setController(controller);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mUserResult.getLink()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final SearchBundle.UserResult content) {
        mUserResult = content;

        final String[] images = content.getImages();
        if (images == null || images.length == 0) {
            mImage.setImageURI((String) null);
        } else {
            fetchImages(mUserResult, images, 0);
        }

        mTitle.setText(content.getTitle());

        if (content.hasDescription()) {
            mBio.setText(content.getDescription());
            mBio.setVisibility(VISIBLE);
        } else {
            mBio.setVisibility(GONE);
        }
    }

}
