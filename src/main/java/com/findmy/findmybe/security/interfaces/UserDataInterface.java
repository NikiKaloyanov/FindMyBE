package com.findmy.findmybe.security.interfaces;

import com.findmy.findmybe.payload.response.PendingLocation;
import com.findmy.findmybe.payload.response.UserLocation;

import java.math.BigDecimal;
import java.util.List;

public interface UserDataInterface {
    void saveLocation(String username, BigDecimal latitude, BigDecimal longitude);

    void addNewSharedLocation(String caller, String email);

    List<UserLocation> getKnownLocations(String caller);

    List<PendingLocation> getPendingLocations(String sender);
}
