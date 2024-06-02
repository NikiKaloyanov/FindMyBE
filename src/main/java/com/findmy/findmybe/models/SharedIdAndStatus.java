package com.findmy.findmybe.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SharedIdAndStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long senderId;

    @ManyToOne()
    private User reader;

    private Boolean isPending;

    public SharedIdAndStatus() {
        this.isPending = true;
    }
}
