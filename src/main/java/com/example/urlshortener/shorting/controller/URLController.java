package com.example.urlshortener.shorting.controller;

import com.example.urlshortener.registration.entity.User;
import com.example.urlshortener.registration.repository.UserRepository;
import com.example.urlshortener.shorting.DTOs.URLRequest;
import com.example.urlshortener.shorting.DTOs.URLResponse;
import com.example.urlshortener.shorting.entity.URL;
import com.example.urlshortener.shorting.repository.URLRepository;
import com.example.urlshortener.shorting.service.URLService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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
    public List<URL> getStudents() {
        return urlService.getURLs();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void redirectURL(@PathVariable Long id ,HttpServletResponse httpServletResponse) {
        urlService.redirectURL(id, httpServletResponse);
    }

    @PostMapping("/administration/short")
    public URLResponse shortenURL(@RequestBody URLRequest longUrl,
                                  @RequestHeader("username") String username,
                                  @RequestHeader("password") String password) {

        Optional<User> user = userRepository.findUserByUsername(username);
        URLResponse badResponse1 = new URLResponse();
        badResponse1.setDescription("User not found!");
        URLResponse badResponse2 = new URLResponse();
        badResponse2.setDescription("Incorrect password!");

        if(!user.isPresent()){
            return badResponse1;
        } else if(!password.equals(user.get().getPassword())){
            return badResponse2;
        }

        return urlService.shortenURL(longUrl);
    }
}
