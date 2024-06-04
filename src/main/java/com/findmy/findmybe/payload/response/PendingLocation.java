package com.findmy.findmybe.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingLocation {
    private String username;

    public PendingLocation(String username) {
        this.username = username;
    }
}
