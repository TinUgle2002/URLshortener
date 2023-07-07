package com.example.urlshortener.shorting.controller;

import com.example.urlshortener.registration.entity.User;
import com.example.urlshortener.registration.repository.UserRepository;
import com.example.urlshortener.shorting.DTOs.Statistics;
import com.example.urlshortener.shorting.DTOs.URLRequest;
import com.example.urlshortener.shorting.DTOs.URLResponse;
import com.example.urlshortener.shorting.entity.URL;
import com.example.urlshortener.shorting.repository.URLRepository;
import com.example.urlshortener.shorting.service.URLService;
import com.example.urlshortener.shorting.util.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class URLController {

    private final URLService urlService;
    private final UserRepository userRepository;
    private final URLRepository urlRepository;

    @Autowired
    public URLController(URLService urlService, UserRepository userRepository, UserRepository urlRepository, URLRepository urlRepository1) {
        this.urlService = urlService;
        this.userRepository = userRepository;
        this.urlRepository = urlRepository1;
    }

    @GetMapping
    public List<URL> getURLs() {
        return urlService.getURLs();
    }

    @GetMapping("/administration/statistics")
    public List<Statistics> getStatistics() {
        return urlService.getStatistics();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void redirectURL(@PathVariable Long id ,HttpServletResponse httpServletResponse) {
        urlService.redirectURL(id, httpServletResponse);
    }

    @PostMapping("/administration/short")
    public URLResponse shortenURL(@RequestBody URLRequest longUrl, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return Authentication.createNullHeaderResponse();
        }

        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] usernamePassword = credentials.split(":", 2);
        String username = usernamePassword[0];
        String password = usernamePassword[1];

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (!user.isPresent()) {
            return Authentication.createUnauthorizedResponse();
        }

        return urlService.shortenURL(longUrl);
    }
}
