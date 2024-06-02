package com.findmy.findmybe.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLocation {
    private String username;
    private String latitude;
    private String longitude;

    public UserLocation() {};

    public UserLocation(String username, String latitude, String longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    };

}
