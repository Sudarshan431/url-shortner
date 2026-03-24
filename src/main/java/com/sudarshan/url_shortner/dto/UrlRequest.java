package com.sudarshan.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UrlRequest {

    @NotBlank(message = "URL cannot be empty")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http:// or https://")
    private String originalUrl;

    private Long expiryInMinutes;

    public UrlRequest(Long expiryInMinutes) {
        this.expiryInMinutes = expiryInMinutes;
    }

    public UrlRequest() {}

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
