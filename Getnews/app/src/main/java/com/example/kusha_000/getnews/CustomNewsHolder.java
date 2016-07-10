package com.example.kusha_000.getnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kusha_000 on 29-06-2016.
 */
public class CustomNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView articleTitle;
    TextView articleSectionName;
    ImageView articleThumbnail;
    Context mContext;

    public CustomNewsHolder(View view, Context mContext) {
        super(view);
        view.setOnClickListener(this);
        this.mContext = mContext;
        articleTitle = (TextView) view.findViewById(R.id.article_title);
        articleSectionName = (TextView) view.findViewById(R.id.article_section_name);
        articleThumbnail = (ImageView) view.findViewById(R.id.article_thumbnail);
    }

    @Override
    public void onClick(View view) {
        NewsArticle newsArticle = MainActivity.newsArticleArrayList.get(getAdapterPosition());
        Intent browserIntent =
                new Intent("android.intent.action.VIEW", Uri.parse(newsArticle.getWebUrl()));
        mContext.startActivity(browserIntent);
    }
}
