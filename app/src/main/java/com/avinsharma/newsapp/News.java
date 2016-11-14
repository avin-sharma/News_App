package com.avinsharma.newsapp;

/**
 * Created by Avin on 14-11-2016.
 */
public class News {
    private String mTitle;
    private String mType;
    private String mDate;
    private String mWebUrl;
    private String mSection;


    public News(String mTitle, String mType, String mDate, String mWebUrl, String mSection) {
        this.mTitle = mTitle;
        this.mType = mType;
        this.mDate = mDate;
        this.mWebUrl = mWebUrl;

        this.mSection = mSection;
    }

    public String getmTitle() {

        return mTitle;
    }

    public String getmType() {
        return mType;
    }

    public String getmDate() {
        return mDate;
    }


    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmSection() {
        return mSection;
    }
}
