package com.findmy.findmybe.repository;

import com.findmy.findmybe.models.LocationCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationCoordinates, Long> {
}
