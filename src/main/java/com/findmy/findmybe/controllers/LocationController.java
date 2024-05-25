package com.findmy.findmybe.controllers;

import com.findmy.findmybe.payload.request.SaveLocationRequest;
import com.findmy.findmybe.security.interfaces.SaveLocationInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app")
public class LocationController {
    SaveLocationInterface saveLocationInterface;

    @Autowired
    public LocationController(SaveLocationInterface saveLocationInterface) {
        this.saveLocationInterface = saveLocationInterface;
    }

    @PostMapping("/updateLocation")
    public ResponseEntity<String> saveLocation(@Valid @RequestBody SaveLocationRequest saveLocationRequest) {
        saveLocationInterface.saveLocation(
                saveLocationRequest.getUsername(),
                new BigDecimal(saveLocationRequest.getLatitude()),
                new BigDecimal(saveLocationRequest.getLongitude())
        );
        return ResponseEntity.ok("Location saved");
    }

}
