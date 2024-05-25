package com.findmy.findmybe.security.interfaces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;

public interface SaveLocationInterface {
    void saveLocation(String username, BigDecimal latitude, BigDecimal longitude) throws UsernameNotFoundException;
}
