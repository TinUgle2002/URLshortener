package com.example.urlshortener.shorting.DTOs;

public class URLRequest {

    private String url;
    private int redirectType;

    public URLRequest(String url, int redirectType) {
        this.url = url;
        this.redirectType = redirectType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(int redirectType) {
        this.redirectType = redirectType;
    }
}
