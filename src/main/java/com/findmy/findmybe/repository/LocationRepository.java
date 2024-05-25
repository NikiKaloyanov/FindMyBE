package com.findmy.findmybe.repository;

import com.findmy.findmybe.models.LocationCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationCoordinates, Long> {
}
