package com.example.kusha_000.getnews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<NewsArticle> newsArticleArrayList;
    TextView Text;
    RecyclerView newsList;
    CustomNewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsArticleArrayList = new ArrayList<>();
        Text = (TextView) findViewById(R.id.Text);
        newsList = (RecyclerView) findViewById(R.id.newsList);
        newsList.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new CustomNewsAdapter(MainActivity.this, newsArticleArrayList);
        newsList.setAdapter(newsAdapter);
        if (isInternetConnected()) {
            new FetchingNews(newsArticleArrayList, this).execute("android");
            setText("getting news for you,thanx for waitimg...");
        } else {
            setText("internet not available,try again later");
        }
    }

    public boolean isInternetConnected() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            setText("net not available,try again later");
            return false;
        }
    }

    private void setText(String errorText) {
        Text.setVisibility(View.VISIBLE);
        Text.setText(errorText);
    }
}
