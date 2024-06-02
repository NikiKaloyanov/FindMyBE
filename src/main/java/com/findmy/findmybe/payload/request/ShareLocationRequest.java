package com.findmy.findmybe.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareLocationRequest {
    private String usernameCaller;
    private String requestedEmail;
}
