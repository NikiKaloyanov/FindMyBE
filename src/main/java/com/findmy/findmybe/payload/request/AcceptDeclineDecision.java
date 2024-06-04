package com.findmy.findmybe.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptDeclineDecision {
    String sender;
    String reader;
    Boolean decision;
}
