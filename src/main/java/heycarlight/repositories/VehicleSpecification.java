package heycarlight.repositories;

import heycarlight.entities.Vehicle;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class VehicleSpecification {

    public static ListingSpecification<Vehicle> hasEqualFields(@NonNull String fieldName, @Nullable Object fieldValue) {
        return new ListingSpecification<Vehicle>() {
            @Override
            public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(fieldName), fieldValue);
            }
        };
    }
}
