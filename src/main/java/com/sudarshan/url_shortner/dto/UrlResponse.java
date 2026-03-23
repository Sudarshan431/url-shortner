package com.sudarshan.url_shortner.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UrlResponse {



    private String shortCode;


    public UrlResponse(String shortCode) {

        this.shortCode = shortCode;

    }

    public String getShortCode() {
        return shortCode;
    }
}
