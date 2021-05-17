package org.jsallari.repositories;

import org.jsallari.entities.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer,Long> {
}
