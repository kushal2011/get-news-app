package com.example.kusha_000.getnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kusha_000 on 29-06-2016.
 */
public class CustomNewsAdapter extends RecyclerView.Adapter<CustomNewsHolder> {
    private List<NewsArticle> mNewsArticles;
    private Context mContext;

    public CustomNewsAdapter(Context context, List<NewsArticle> newsArticles) {
        this.mNewsArticles = newsArticles;
        this.mContext = context;
    }

    @Override
    public CustomNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_layout, null);
        CustomNewsHolder rcv = new CustomNewsHolder(layoutView, mContext);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CustomNewsHolder holder, int position) {
        NewsArticle currentArticle = mNewsArticles.get(position);
        String articleTitle = currentArticle.getTitle();
        String articleSectionName = currentArticle.getSectionName();
        String articleThumbnail = currentArticle.getThumbnailUrl();
        holder.articleTitle.setText(articleTitle);
        holder.articleSectionName.setText(articleSectionName);
        if (articleThumbnail != null && !articleThumbnail.equals("")) {
            new DownloadImage(holder.articleThumbnail).execute(articleThumbnail);
        } else {
            holder.articleThumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.mNewsArticles.size();
    }
}
