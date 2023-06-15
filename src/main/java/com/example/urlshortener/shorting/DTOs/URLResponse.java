package com.example.urlshortener.shorting.DTOs;


public class URLResponse {

    private String shortURL;
    private String description;

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shorURL) {
        this.shortURL = shorURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
