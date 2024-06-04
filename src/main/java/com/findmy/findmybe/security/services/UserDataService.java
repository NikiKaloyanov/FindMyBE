package com.findmy.findmybe.security.services;

import com.findmy.findmybe.models.LocationCoordinates;
import com.findmy.findmybe.models.SharedIdAndStatus;
import com.findmy.findmybe.models.User;
import com.findmy.findmybe.payload.response.PendingLocation;
import com.findmy.findmybe.payload.response.UserLocation;
import com.findmy.findmybe.repository.LocationRepository;
import com.findmy.findmybe.repository.SharedLocationsRepository;
import com.findmy.findmybe.repository.UserRepository;
import com.findmy.findmybe.security.interfaces.UserDataInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserDataService implements UserDataInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);
    UserRepository userRepository;
    LocationRepository locationRepository;
    SharedLocationsRepository sharedLocationsRepository;

    @Autowired
    public UserDataService(
            UserRepository userRepository,
            LocationRepository locationRepository,
            SharedLocationsRepository sharedLocationsRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.sharedLocationsRepository = sharedLocationsRepository;
    }

    @Override
    public void saveLocation(String username, BigDecimal latitude, BigDecimal longitude) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username)
        );

        if (user.getLastLocation() == null) {
            LocationCoordinates location = new LocationCoordinates(latitude, longitude);
            user.setLastLocation(location);
            userRepository.save(user);
        } else {
            Optional<LocationCoordinates> locationCoordinates = locationRepository.findById(user.getLastLocation().getId());
            if (locationCoordinates.isPresent()) {
                LocationCoordinates tempLocation = locationCoordinates.get();
                tempLocation.setLatitude(latitude);
                tempLocation.setLongitude(longitude);
                locationRepository.save(tempLocation);
            }
        }
    }

    @Override
    public void addNewSharedLocation(String caller, String email) {
        User reader = userRepository.findByUsername(caller).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + caller)
        );
        User sender = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with email: " + email)
        );

        SharedIdAndStatus sharedIdAndStatus = new SharedIdAndStatus();
        sharedIdAndStatus.setIsPending(true);
        sharedIdAndStatus.setSenderId(sender.getId());
        sharedIdAndStatus.setReader(reader);

        List<SharedIdAndStatus> temp = reader.getSharedIdAndStatuses();
        temp.add(sharedIdAndStatus);
        reader.setSharedIdAndStatuses(temp);


        userRepository.save(reader);
    }

    private UserLocation mapperToUserLocation(SharedIdAndStatus sharedIdAndStatus) {
        User sender = userRepository.findById(sharedIdAndStatus.getSenderId()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id: " + sharedIdAndStatus.getSenderId())
        );

        return new UserLocation(
                sender.getUsername(),
                sender.getLastLocation().getLatitude(),
                sender.getLastLocation().getLongitude()
        );
    }

    @Override
    public List<UserLocation> getKnownLocations(String caller) {
        User reader = userRepository.findByUsername(caller).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + caller)
        );

        return reader.getSharedIdAndStatuses()
                .stream().filter(it -> !it.getIsPending())
                .map(this::mapperToUserLocation).toList();
    }

    private PendingLocation mapperToPendingLocation(Long requesterId) {
        User requester = userRepository.findById(requesterId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id: " + requesterId)
        );

        return new PendingLocation(requester.getUsername());
    }

    @Override
    public List<PendingLocation> getPendingLocations(String reviewerName) {
        User reviewer = userRepository.findByUsername(reviewerName).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + reviewerName)
        );

        List<SharedIdAndStatus> sharedIdAndStatuses = sharedLocationsRepository.findBySenderId(reviewer.getId()).orElseThrow(
                () -> new UsernameNotFoundException("Locations not found for reviewer: " + reviewerName)
        );

        return sharedIdAndStatuses
                .stream().filter(SharedIdAndStatus::getIsPending)
                .map(it -> mapperToPendingLocation(it.getReader().getId()))
                .toList();
    }

    @Override
    public void acceptDeclinePendingLocation(String sender, String reader, Boolean decision) {
        User senderUser = userRepository.findByUsername(sender).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + sender)
        );

        User reviewer = userRepository.findByUsername(reader).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + reader)
        );

        List<SharedIdAndStatus> sharedIdAndStatuses = sharedLocationsRepository.findBySenderId(senderUser.getId()).orElseThrow(
                () -> new UsernameNotFoundException("Locations not found for reviewer: " + senderUser)
        );

        SharedIdAndStatus temp = sharedIdAndStatuses.stream()
                .filter(it -> it.getIsPending() && it.getSenderId().equals(senderUser.getId()))
                .filter(it -> it.getReader().getId().equals(reviewer.getId()))
                .findAny().orElse(null);

        if (temp != null) {
            if (decision) {
                temp.setIsPending(false);
                sharedLocationsRepository.save(temp);
            } else {
                sharedLocationsRepository.deleteById(temp.getId());
            }
        }
    }

}