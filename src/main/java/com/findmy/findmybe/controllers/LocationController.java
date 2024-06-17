package com.findmy.findmybe.controllers;

import com.findmy.findmybe.payload.request.*;
import com.findmy.findmybe.payload.response.PendingLocation;
import com.findmy.findmybe.payload.response.UserLocation;
import com.findmy.findmybe.security.interfaces.UserDataInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app")
public class LocationController {
    UserDataInterface userDataInterface;

    @Autowired
    public LocationController(UserDataInterface userDataInterface) {
        this.userDataInterface = userDataInterface;
    }

    @PostMapping("/updateLocation")
    public ResponseEntity<String> saveLocation(@Valid @RequestBody SaveLocationRequest saveLocationRequest) {
        userDataInterface.saveLocation(saveLocationRequest.getUsername(), new BigDecimal(saveLocationRequest.getLatitude()), new BigDecimal(saveLocationRequest.getLongitude()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendLocationShareRequest")
    public ResponseEntity<String> sendLocationShareRequest(@Valid @RequestBody ShareLocationRequest shareLocationRequest) {
        userDataInterface.addNewSharedLocation(shareLocationRequest.getUsernameCaller(), shareLocationRequest.getRequestedEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getPendingLocations")
    public ResponseEntity<List<PendingLocation>> getPendingLocation(@Valid @RequestBody Caller caller) {
        return ResponseEntity.ok(userDataInterface.getPendingLocations(caller.getCaller()));
    }

    @PostMapping("/getKnownLocations")
    public ResponseEntity<List<UserLocation>> getKnownLocations(@Valid @RequestBody Caller caller) {
        return ResponseEntity.ok(userDataInterface.getKnownLocations(caller.getCaller()));
    }

    @PostMapping("/acceptDeclinePendingLocation")
    public ResponseEntity<String> acceptDeclinePendingLocation(@Valid @RequestBody AcceptDeclineDecision decision) {
        userDataInterface.acceptDeclinePendingLocation(decision.getSender(), decision.getReader(), decision.getDecision());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setMessage")
    public ResponseEntity<String> setUserMessage(@Valid @RequestBody MessageSetter messageSetter) {
        userDataInterface.setMessage(messageSetter.getUser(), messageSetter.getMessage());
        return ResponseEntity.ok("Success");
    }

    @PostMapping("deleteSharedLocation")
    public ResponseEntity<String> deleteSharedLocation(@Valid @RequestBody DeleteRequest deleteRequest) {
        userDataInterface.deleteSharedLocation(deleteRequest.getSender(), deleteRequest.getReader());
        return ResponseEntity.ok("Deleted");
    }


}
