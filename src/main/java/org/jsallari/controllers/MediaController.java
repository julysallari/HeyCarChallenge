package org.jsallari.controllers;

import org.jsallari.entities.Vehicle;
import org.jsallari.services.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MediaController {

    @Autowired
    private DealerService dealerService;

    @PostMapping(value = "/upload_csv/{dealer_id}/vehicles", consumes = "text/csv")
    public ResponseEntity<String> uploadVehicles(@RequestBody @Valid List<Vehicle> listing, @PathVariable("dealer_id") String dealerId) {
        this.dealerService.uploadListing(listing, dealerId);
        return ResponseEntity.ok("Data successfully uploaded");
    }
}
