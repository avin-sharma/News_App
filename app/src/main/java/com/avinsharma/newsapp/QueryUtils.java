package com.avinsharma.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Avin on 14-11-2016.
 */
public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating Url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<News> extractNews(String jsonResponse){
        ArrayList<News> news = new ArrayList<>();
        if (jsonResponse == null)
            return news;

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray results = root.getJSONObject("response").getJSONArray("results");
            JSONObject info;
            String title;
            String type;
            String date;
            String webUrl;
            String section;

            for (int i=0;i<results.length();i++){
                info = results.getJSONObject(i);
                title = info.getString("webTitle");
                type = info.getString("type");
                date = info.getString("webPublicationDate");
                webUrl = info.getString("webUrl");
                section = info.getString("sectionId");
                news.add(new News(title,type,date,webUrl,section));
            }

        }catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return news;
    }

    public static ArrayList<News> fetchNews(String stringUrl){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(stringUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error getting json response", e);
        }
        return extractNews(jsonResponse);
    }
}
