package com.sudarshan.url_shortner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UrlRequest {

    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl (String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
