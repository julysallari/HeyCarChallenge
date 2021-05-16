package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.jsallari.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.jsallari.repositories.VehicleSpecification.hasEqualFields;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findByFields(Map<String, Object> fields) {
        Specification<Vehicle> specification = null;
        for (Map.Entry<String,Object> entry : fields.entrySet()) {
            Object val = entry.getValue();
            if (val != null) {
                if(specification == null) {
                    specification = where(hasEqualFields(entry.getKey(),val));
                } else {
                    specification = specification.and(where(hasEqualFields(entry.getKey(),val)));
                }
            }
        }
        return this.vehicleRepository.findAll(specification);
    }

    public List<Vehicle> findAll() {
        return Lists.newArrayList(this.vehicleRepository.findAll());
    }
}
