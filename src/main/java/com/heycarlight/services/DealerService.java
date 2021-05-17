package com.heycarlight.services;

import com.heycarlight.entities.Dealer;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface DealerService {
    void addDealer(@NonNull Dealer dealer);
    Optional<Dealer> findById(UUID id);
}
