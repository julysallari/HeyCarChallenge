package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListingService {

    void uploadVehicles(List<Vehicle> listing, @NonNull String dealerId);
    void uploadVehicles(MultipartFile fileListing, @NonNull String dealerId);
}
