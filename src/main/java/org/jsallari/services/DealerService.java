package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DealerService {

    void uploadListing(List<Vehicle> listing, @NonNull String dealerId);
}
