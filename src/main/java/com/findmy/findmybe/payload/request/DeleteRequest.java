package com.findmy.findmybe.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRequest {
    String sender;
    String reader;
}
