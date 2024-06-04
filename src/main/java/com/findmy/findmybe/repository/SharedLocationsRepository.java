package com.findmy.findmybe.repository;

import com.findmy.findmybe.models.SharedIdAndStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharedLocationsRepository extends JpaRepository<SharedIdAndStatus, Long> {
    Optional<List<SharedIdAndStatus>> findByReaderId(Long readerId);
    Optional<List<SharedIdAndStatus>> findBySenderId(Long senderId);
}
