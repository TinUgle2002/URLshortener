package com.example.urlshortener.shorting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "URLs")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "longURL")
    private String url;
    private String shortUrl;
    @Column(name = "redirects")
    private int redirects;
    private int redirectType;


    public URL(String long_url, int redirectType) {
        this.url = long_url;
        this.redirectType = redirectType;
    }

    public URL() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String long_url) {
        this.url = long_url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String short_url) {
        this.shortUrl = short_url;
    }

    public int getRedirects() {
        return redirects;
    }

    public int getRediredtType() {
        return redirectType;
    }

    public void setRediredtType(int redirectType) {
        this.redirectType = redirectType;
    }

    public void incrementRedirects() {
        this.redirects++;
    }

}
