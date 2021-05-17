package com.heycarlight.services;

import com.heycarlight.entities.Vehicle;
import com.heycarlight.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.heycarlight.repositories.VehicleSpecification.hasEqualFields;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SearchService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findByFields(Map<String, Object> fields) {
        Specification<Vehicle> specification = Specification.where(null);
        for (Map.Entry<String,Object> entry : fields.entrySet()) {
            Object val = entry.getValue();
            if (val != null) {
                specification = specification.and(where(hasEqualFields(entry.getKey(),val)));
            }
        }
        return this.vehicleRepository.findAll(specification);
    }
}
