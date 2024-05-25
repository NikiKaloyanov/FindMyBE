package com.findmy.findmybe.payload.request;

import lombok.Getter;

@Getter
public class SaveLocationRequest {
    private String username;
    private String longitude;
    private String latitude;
}
