package com.example.kusha_000.getnews;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kusha_000 on 29-06-2016.
 */
public class FetchingNews extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = FetchingNews.class.getSimpleName();
    MainActivity mainActivity;
    private ArrayList<NewsArticle> newsArticles;

    FetchingNews(ArrayList<NewsArticle> news, MainActivity mainActivity) {
        newsArticles = news;
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params == null) return null;
        String topic = params[0];
        return fetchNews(topic);
    }

    @Override
    protected void onPostExecute(String apiResponse) {
        if (apiResponse != null) {
            parseJSONResponse(apiResponse);
            mainActivity.newsAdapter.notifyDataSetChanged();
            mainActivity.Text.setVisibility(View.GONE);
        } else {
            mainActivity.Text.setText("news not available");
            mainActivity.Text.setVisibility(View.VISIBLE);
        }
    }

    private String fetchNews(String topic) {
        final String BASE_URL = "http://content.guardianapis.com/"
                + "search?q="
                + topic
                + "&api-key=test&show-fields=thumbnail";
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection conn = null;
        String jsonResponse = null;
        try {
            URL url = new URL(BASE_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();
            if (response != 200) {
                return null;
            }
            inputStream = conn.getInputStream();
            if (inputStream == null) {
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder stringBuilder = new StringBuilder("");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            if (stringBuilder.length() == 0) {
                return null;
            }
            jsonResponse = stringBuilder.toString();
            Log.d(LOG_TAG, jsonResponse);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        } finally {
            if (conn != null) {
            }
            conn.disconnect();
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }

    private void parseJSONResponse(String jsonResponse) {
        try {
            JSONObject newsJSONObject = new JSONObject(jsonResponse);
            JSONObject responseJSONObject = newsJSONObject.getJSONObject("response");
            JSONArray resultsArray = responseJSONObject.getJSONArray("results");
            int resultsCount = resultsArray.length();
            for (int i = 0; i < resultsCount; i++) {
                JSONObject newsObject = resultsArray.getJSONObject(i);
                String title = newsObject.getString("webTitle");
                String webUrl = newsObject.getString("webUrl");
                String sectionName = newsObject.getString("sectionName");
                String thumbnail = null;
                if (newsObject.has("fields")) {
                    JSONObject fieldsObject = newsObject.getJSONObject("fields");
                    if (fieldsObject != null) {
                        thumbnail = fieldsObject.getString("thumbnail");
                    }
                }
                newsArticles.add(new NewsArticle(title, sectionName, thumbnail, webUrl));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }
}

