package com.heycarlight.services;

import com.heycarlight.entities.Dealer;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface DealerService {
    void addDealer(@NonNull String name);
    Optional<Dealer> findById(String id);
}