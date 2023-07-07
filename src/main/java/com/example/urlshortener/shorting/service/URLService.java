package com.example.urlshortener.shorting.service;

import com.example.urlshortener.shorting.DTOs.Statistics;
import com.example.urlshortener.shorting.DTOs.URLRequest;
import com.example.urlshortener.shorting.DTOs.URLResponse;
import com.example.urlshortener.shorting.entity.URL;
import com.example.urlshortener.shorting.repository.URLRepository;
import com.example.urlshortener.shorting.util.Authentication;
import com.example.urlshortener.shorting.util.RedirectType;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class URLService {


    Properties appProp = new Properties();
    @PostConstruct
    public void initProperties() {
        try {
            InputStream input = new FileInputStream("src/main/resources/application.properties");
            appProp.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private final URLRepository urlRepository;

    @Autowired
    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    @Transactional
    public URLResponse shortenURL(URLRequest longUrl) {

        URLResponse response = new URLResponse();
        Optional<URL> urlByName = urlRepository.findByUrl(longUrl.getUrl());

        if (urlByName.isPresent()) {
            response.setDescription("Failed! URL has already been shortened!");
        } else if (longUrl.getUrl().equals("") || longUrl.getUrl() == null) {
            response.setDescription("Failed! URL is null.");
        } else if(!Authentication.checkURLIntegrity(longUrl.getUrl())) {
            response.setDescription("Failed! URL not valid");
        } else {
            if (RedirectType.checkValidRedirectType(longUrl.getRedirectType())) {
            URL url = new URL(longUrl.getUrl(), longUrl.getRedirectType());
            urlRepository.save(url);
            response.setShortURL(appProp.getProperty("urlBase") + url.getId());
            url.setShortUrl(response.getShortURL());
            } else {
                response.setDescription("Failed! Invalid redirect type.");
            }
        }

        return response;
    }

    @Transactional
    public void redirectURL(Long id, HttpServletResponse httpServletResponse) {

        Optional<URL> urlById = urlRepository.findURLById(id);
        if(urlById.isPresent()) {
            httpServletResponse.setHeader("Location", urlById.get().getUrl());
            httpServletResponse.setStatus(urlById.get().getRediredtType());
            urlById.get().incrementRedirects();
        }
    }

    public List<URL> getURLs() {
        return urlRepository.findAll();
    }

    public ArrayList<Statistics> getStatistics() {

        List<URL> urlList = urlRepository.findAll();
        ArrayList<Statistics> statList = new ArrayList<>();

        for (URL url : urlList) {
            statList.add(new Statistics(url));
        }

        return statList;
    }
}
