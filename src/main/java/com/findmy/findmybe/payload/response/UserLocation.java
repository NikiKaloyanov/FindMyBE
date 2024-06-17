package com.findmy.findmybe.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UserLocation {
    private String username;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String message;

    public UserLocation() {}

    public UserLocation(String username, BigDecimal latitude, BigDecimal longitude, String message) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
    }

}
