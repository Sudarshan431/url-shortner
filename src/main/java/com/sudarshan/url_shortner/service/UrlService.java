package com.sudarshan.url_shortner.service;

import com.sudarshan.url_shortner.dto.UrlRequest;
import com.sudarshan.url_shortner.dto.UrlResponse;
import com.sudarshan.url_shortner.model.Url;
import com.sudarshan.url_shortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {

    private static final String BASE_URL = "http://localhost:8080";

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);

        String shortCode = generateUniqueCode();
        url.setShortCode(shortCode);

        urlRepository.save(url);

        return BASE_URL + "/" + url.getShortCode();
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomString(6);
        } while (urlRepository.existsByShortCode(code));

        return code;
    }

    private String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
