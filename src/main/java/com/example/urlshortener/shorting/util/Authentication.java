package com.example.urlshortener.shorting.util;

import com.example.urlshortener.shorting.DTOs.URLResponse;

public class Authentication {

    public static URLResponse createUnauthorizedResponse() {

        URLResponse response = new URLResponse();
        response.setDescription("Unauthorized! Wrong username or password.");

        return response;
    }

    public static URLResponse createNullHeaderResponse() {

        URLResponse response = new URLResponse();
        response.setDescription("Unauthorized! Username and password required.");

        return response;
    }

    public static boolean checkURLIntegrity(String url){

        String[] tmp = url.split("/");

        return tmp[0].equals("http:") || tmp[0].equals("https:");
    }
}
