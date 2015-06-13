package com.alexvasilkov.gestures.sample.items;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Intents;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.sample.R;
import com.alexvasilkov.gestures.views.GestureLayout;
import com.bumptech.glide.Glide;

public class PaintingsLayoutsAdapter extends PagerAdapter implements View.OnClickListener {

    private final ViewPager mViewPager;
    private final Painting[] mPaintings;

    public PaintingsLayoutsAdapter(ViewPager pager, Painting[] paintings) {
        mViewPager = pager;
        mPaintings = paintings;
    }

    @Override
    public int getCount() {
        return mPaintings.length;
    }

    @Override
    public View instantiateItem(final ViewGroup container, int position) {
        Context context = container.getContext();
        View layout = Views.inflate(container, R.layout.activity_layout_item);
        final int match = ViewGroup.LayoutParams.MATCH_PARENT;
        container.addView(layout, match, match);

        GestureLayout gLayout = Views.find(layout, R.id.painting_g_layout);
        gLayout.getController().getSettings().setOverscrollDistance(context, 32, 0);
        gLayout.enableScrollInViewPager(mViewPager);

        ImageView image = Views.find(layout, R.id.painting_image);
        Glide.with(context).load(mPaintings[position].getImageId()).into(image);

        TextView title = Views.find(layout, R.id.painting_title);
        CharSequence titleText = new SpannableBuilder(context)
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.paintings_author).append("\n")
                .clearStyle()
                .append(mPaintings[position].getTitle())
                .build();
        title.setText(titleText);

        View button = Views.find(layout, R.id.painting_button);
        button.setTag(mPaintings[position].getLink());
        button.setOnClickListener(this);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onClick(@NonNull View view) {
        Intents.get(view.getContext()).openWebBrowser((String) view.getTag());
    }

}
