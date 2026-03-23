package com.sudarshan.url_shortner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UrlRequest {

    private String originalUrl;

    private Long expiryInMinutes;

    public UrlRequest(Long expiryInMinutes) {
        this.expiryInMinutes = expiryInMinutes;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl (String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getExpiryInMinutes() {
        return expiryInMinutes;
    }

    public void setExpiryInMinutes(Long expiryInMinutes) {
        this.expiryInMinutes = expiryInMinutes;
    }
}
