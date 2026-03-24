package com.sudarshan.url_shortner.controller;

import com.sudarshan.url_shortner.dto.UrlRequest;
import com.sudarshan.url_shortner.dto.UrlResponse;
import com.sudarshan.url_shortner.model.Url;
import com.sudarshan.url_shortner.repository.UrlRepository;
import com.sudarshan.url_shortner.service.UrlService;
import jakarta.validation.Valid;
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
    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/shorten")
    public UrlResponse shorten(@Valid @RequestBody UrlRequest urlRequest) {
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

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<Integer> getClicks(@PathVariable String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        return ResponseEntity.ok(url.getClickCount());
    }
}
