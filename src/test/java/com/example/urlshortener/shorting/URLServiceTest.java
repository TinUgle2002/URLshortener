package com.example.urlshortener.shorting;

import com.example.urlshortener.shorting.DTOs.URLRequest;
import com.example.urlshortener.shorting.DTOs.URLResponse;
import com.example.urlshortener.shorting.entity.URL;
import com.example.urlshortener.shorting.repository.URLRepository;
import com.example.urlshortener.shorting.service.URLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class URLServiceTest {

    @InjectMocks
    private URLService urlService;

    @Mock
    private URLRepository urlRepository;

    Properties appProp = new Properties();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        urlService = new URLService(urlRepository);

        try {
            InputStream input = new FileInputStream("src/main/resources/application.properties");
            appProp.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }    }

    @Test
    public void shortenURLTestSuccess() {

        URLRequest url1 = new URLRequest("http://google.com", 302);

        URLResponse response1 = urlService.shortenURL(url1);
        URL longUrl = new URL(url1.getUrl(), url1.getRedirectType());

        Mockito.doReturn(Optional.of(longUrl))
                .when(urlRepository).findByUrl(url1.getUrl());

        assertEquals(response1.getShortURL(), appProp.getProperty("baseUrl") + longUrl.getId());
        assertNull(response1.getDescription());
    }

    @Test
    public void shortenURLTestFail() {

        //original
        URLRequest url1 = new URLRequest("http://hcl.hr", 302);

        URLResponse response1 = urlService.shortenURL(url1);
        URL longUrl1 = new URL(url1.getUrl(), url1.getRedirectType());

        Mockito.doReturn(Optional.of(longUrl1))
                .when(urlRepository).findByUrl(url1.getUrl());

        assertEquals(response1.getShortURL(), appProp.getProperty("baseUrl") + longUrl1.getId());
        assertNull(response1.getDescription());

        //duplicate URL
        URLRequest url2 = new URLRequest("http://hcl.hr", 302);

        URLResponse response2 = urlService.shortenURL(url2);
        URL longUrl2 = new URL(url2.getUrl(), url2.getRedirectType());

        Mockito.doReturn(Optional.of(longUrl2))
                .when(urlRepository).findByUrl(url1.getUrl());

        assertNull(response2.getShortURL());
        assertEquals(response2.getDescription(), "Failed! URL has already been shortened!");
    }

}
