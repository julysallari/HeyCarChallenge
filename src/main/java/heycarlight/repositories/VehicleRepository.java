package heycarlight.repositories;

import heycarlight.entities.Vehicle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findAll(Specification<Vehicle> where);

    List<Vehicle> findByDealerId(UUID dealerId);
}
