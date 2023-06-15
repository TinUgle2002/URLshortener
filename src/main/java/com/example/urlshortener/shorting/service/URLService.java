package com.example.urlshortener.shorting.service;

import com.example.urlshortener.shorting.DTOs.URLRequest;
import com.example.urlshortener.shorting.DTOs.URLResponse;
import com.example.urlshortener.shorting.entity.URL;
import com.example.urlshortener.shorting.repository.URLRepository;
import com.example.urlshortener.shorting.util.Shortening;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class URLService {

    private final URLRepository urlRepository;

    @Autowired
    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public URLResponse shortenURL(URLRequest longUrl) {

        URLResponse response = new URLResponse();
        Optional<URL> urlByName = urlRepository.findByUrl(longUrl.getUrl());

        if(urlByName.isPresent()) {
            response.setDescription("Failed because URL has already been shortened!");
        } else {
            URL url = new URL(longUrl.getUrl(), response.getShortURL(), longUrl.getRedirectType());
            urlRepository.save(url);
            response.setShortURL(Shortening.shorten(url.getId()));
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
}
