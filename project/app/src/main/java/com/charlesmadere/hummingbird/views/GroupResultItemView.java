package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.adapters.SearchResultsAdapter;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupResultItemView extends CardView implements AdapterView<Void>,
        SearchResultsAdapter.Handler, View.OnClickListener {

    private SearchBundle.GroupResult mGroupResult;

    @BindView(R.id.sdvLogo)
    SimpleDraweeView mLogo;

    @BindView(R.id.tvDescription)
    TextView mDescription;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.divider)
    View mDivider;


    public GroupResultItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupResultItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void fetchImages(final SearchBundle.GroupResult groupResult, final String[] images,
            final int index) {
        if (mGroupResult != groupResult) {
            return;
        } else if (index >= images.length) {
            mLogo.setImageURI((String) null);
            return;
        }

        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        super.onFailure(id, throwable);
                        fetchImages(groupResult, images, index + 1);
                    }
                })
                .setOldController(mLogo.getController())
                .setUri(images[index])
                .build();

        mLogo.setController(controller);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(GroupActivity.getLaunchIntent(context, mGroupResult.getLink(),
                mGroupResult.getTitle()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final SearchBundle.AbsResult result, final boolean showDivider) {
        setContent((SearchBundle.GroupResult) result, showDivider);
    }

    public void setContent(final SearchBundle.GroupResult result, final boolean showDivider) {
        mGroupResult = result;

        final String[] images = result.getImages();
        if (images == null || images.length == 0) {
            mLogo.setImageURI((String) null);
        } else {
            fetchImages(result, images, 0);
        }

        mTitle.setText(result.getTitle());

        if (result.hasDescription()) {
            mDescription.setText(result.getDescription());
            mDescription.setVisibility(VISIBLE);
        } else {
            mDescription.setVisibility(GONE);
        }

        mDivider.setVisibility(showDivider ? VISIBLE : GONE);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
