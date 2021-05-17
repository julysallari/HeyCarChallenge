package org.jsallari.repositories;

import org.jsallari.entities.Vehicle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAll(Specification<Vehicle> where);

    List<Vehicle> findByDealerId(Long dealerId);
}
