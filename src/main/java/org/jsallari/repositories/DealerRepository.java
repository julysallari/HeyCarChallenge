package org.jsallari.repositories;

import org.jsallari.entities.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {
}
