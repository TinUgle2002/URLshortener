package com.example.urlshortener.shorting.DTOs;

import com.example.urlshortener.shorting.entity.URL;

public class Statistics {

    private String longURL;
    private int redirects;

    public Statistics(URL url) {
        this.longURL = url.getUrl();
        this.redirects = url.getRedirects();
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public int getRedirects() {
        return redirects;
    }

    public void setRedirects(int redirects) {
        this.redirects = redirects;
    }
}
