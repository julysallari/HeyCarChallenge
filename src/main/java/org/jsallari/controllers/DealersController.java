package org.jsallari.controllers;

import org.jsallari.entities.Vehicle;
import org.jsallari.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dealers")
public class DealersController {

    @Autowired
    private ListingService dealerService;

    @PostMapping(value = "/{dealer_id}/vehicle_listings/upload", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadVehiclesJson(@RequestBody @Valid List<Vehicle> listing, @PathVariable("dealer_id") String dealerId) {
        this.dealerService.uploadListing(listing, dealerId);
        return ResponseEntity.ok("Data successfully uploaded");
    }
}
