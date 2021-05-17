package heycarlight.services;

import heycarlight.controllers.VehicleRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface ListingService {

    List<String> uploadVehicles(@Valid List<VehicleRequest> listing, @NonNull String dealerId);
    List<String> uploadVehicles(MultipartFile fileListing, @NonNull String dealerId);
}
