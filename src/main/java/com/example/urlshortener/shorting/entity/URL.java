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
    private String short_url;
    @Column(name = "redirects")
    private int redirects;
    private int redirectType;


    public URL(String long_url, String short_url,int redirectType) {
        this.url = long_url;
        this.short_url = short_url;
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

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
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
