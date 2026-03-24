package com.sudarshan.url_shortner.service;

import com.sudarshan.url_shortner.dto.UrlRequest;
import com.sudarshan.url_shortner.dto.UrlResponse;
import com.sudarshan.url_shortner.model.Url;
import com.sudarshan.url_shortner.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private static final String BASE_URL = "http://localhost:8080";

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl, Long expiryInMinutes) {

        validateUrl(originalUrl);
        checkMalicious(originalUrl);

        Url url = new Url();
        url.setOriginalUrl(originalUrl);

        String shortCode = generateUniqueCode();
        url.setShortCode(shortCode);

        if(expiryInMinutes != null) {
            url.setExpiryDate(LocalDateTime.now().plusMinutes(expiryInMinutes));
        }
        else {
            url.setExpiryDate(LocalDateTime.now().plusHours(24));
        }

        urlRepository.save(url);

        return BASE_URL + "/" + url.getShortCode();
    }

    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (url.getExpiryDate() != null &&
                LocalDateTime.now().isAfter(url.getExpiryDate())) {
            throw new ResponseStatusException(
                    HttpStatus.GONE, "Link Expired");
        }

        url.setClickCount(url.getClickCount() + 1);

        urlRepository.save(url);

        return url.getOriginalUrl();
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

    private void validateUrl(String originalUrl) {
        try {
            URI uri = new URI(originalUrl);

            String scheme = uri.getScheme();

            if(scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
                throw new RuntimeException("Only HTTP/HTTPS URLs allowed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Malformed URL");
        }
    }

    private void checkMalicious(String originalUrl) {

        String lowerUrl = originalUrl.toLowerCase();

        if (lowerUrl.startsWith("javascript:") ||
                lowerUrl.startsWith("data:") ||
                lowerUrl.startsWith("files:")) {
            throw new RuntimeException("Malicious URL detected");
        }
    }
}
