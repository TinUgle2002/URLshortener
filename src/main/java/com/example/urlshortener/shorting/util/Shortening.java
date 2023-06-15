package com.example.urlshortener.shorting.util;

public class Shortening {

        public static String  shorten(Long id) {
                return "http://localhost:8080/" + id;
        }
}
