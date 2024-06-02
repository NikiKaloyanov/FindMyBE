package com.findmy.findmybe.repository;

import com.findmy.findmybe.models.SharedIdAndStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedLocationsRepository extends JpaRepository<SharedIdAndStatus, Long> {
}
