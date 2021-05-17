package com.heycarlight.services;

import com.heycarlight.controllers.VehicleRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ListingService {

    List<String> uploadVehicles(@Valid List<VehicleRequest> listing, @NonNull UUID dealerId);
    List<String> uploadVehicles(MultipartFile fileListing, @NonNull UUID dealerId);
}
