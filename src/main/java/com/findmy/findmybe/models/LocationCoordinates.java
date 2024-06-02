package com.findmy.findmybe.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class LocationCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision=10, scale=6)
    private BigDecimal latitude;

    @Column(precision=10, scale=6)
    private BigDecimal longitude;

    public LocationCoordinates() {}

    public LocationCoordinates(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
