package heycarlight.services;

import heycarlight.entities.Dealer;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface DealerService {
    void addDealer(@NonNull String name);
    Optional<Dealer> findById(String id);
}
