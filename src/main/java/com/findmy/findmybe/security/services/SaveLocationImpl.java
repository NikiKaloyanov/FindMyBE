package com.findmy.findmybe.security.services;

import com.findmy.findmybe.models.LocationCoordinates;
import com.findmy.findmybe.models.User;
import com.findmy.findmybe.repository.LocationRepository;
import com.findmy.findmybe.repository.UserRepository;
import com.findmy.findmybe.security.interfaces.SaveLocationInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaveLocationImpl implements SaveLocationInterface {
    private static final Logger logger = LoggerFactory.getLogger(SaveLocationImpl.class);
    UserRepository userRepository;
    LocationRepository locationRepository;

    @Autowired
    public SaveLocationImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void saveLocation(String username, BigDecimal latitude, BigDecimal longitude) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        LocationCoordinates location = new LocationCoordinates(latitude, longitude);
        user.setLastLocation(location);

//        locationRepository.save(location);
        userRepository.save(user);
    }
}
