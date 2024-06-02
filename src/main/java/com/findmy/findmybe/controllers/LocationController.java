package com.findmy.findmybe.controllers;

import com.findmy.findmybe.payload.request.Caller;
import com.findmy.findmybe.payload.request.SaveLocationRequest;
import com.findmy.findmybe.payload.request.ShareLocationRequest;
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
        return ResponseEntity.ok("Location saved");
    }

    @PostMapping("/sendLocationShareRequest")
    public ResponseEntity<String> sendLocationShareRequest(@Valid @RequestBody ShareLocationRequest shareLocationRequest) {
        userDataInterface.addNewSharedLocation(shareLocationRequest.getUsernameCaller(), shareLocationRequest.getRequestedEmail());
        return ResponseEntity.ok("Location saved");
    }

    @PostMapping("/getKnownLocations")
    public ResponseEntity<List<UserLocation>> getKnownLocations(@Valid @RequestBody Caller caller) {
        return ResponseEntity.ok(userDataInterface.getKnownLocations(caller.getCaller()));
    }

}
