package com.sudarshan.url_shortner.controller;

import com.sudarshan.url_shortner.dto.UrlRequest;
import com.sudarshan.url_shortner.dto.UrlResponse;
import com.sudarshan.url_shortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/url")
public class Urlcontroller {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public UrlResponse shorten(@RequestBody UrlRequest urlRequest) {
        System.out.println("URL: " + urlRequest.getOriginalUrl());
        String shortUrl = urlService.shortenUrl(urlRequest.getOriginalUrl(), urlRequest.getExpiryInMinutes());
        return new UrlResponse(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
