package com.heycarlight.controllers;

import com.heycarlight.entities.Dealer;
import com.heycarlight.requests.VehicleRequest;
import com.heycarlight.services.DealerService;
import com.heycarlight.responses.UploadResponse;
import com.heycarlight.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dealers")
public class DealerController {

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;

    @PostMapping(value = "/{dealer_id}/vehicle_listings/upload", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadResponse> uploadVehiclesJson(@RequestBody @Valid List<VehicleRequest> listing, @PathVariable("dealer_id") String dealerId) {
        List<String> notUploaded = this.listingService.uploadVehicles(listing, UUID.fromString(dealerId));
        if (notUploaded.isEmpty()) {
            return ResponseEntity.ok(new UploadResponse("Data successfully uploaded"));
        }
        return ResponseEntity.ok(new UploadResponse("Some items could not be uploaded", notUploaded));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDealer(@RequestBody String name){
        this.dealerService.addDealer(new Dealer(name));
    }
}
