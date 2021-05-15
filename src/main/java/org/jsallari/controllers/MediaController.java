package org.jsallari.controllers;

import org.jsallari.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MediaController {

    @Autowired
    private ListingService listingService;

    @PostMapping(value = "/upload_csv/{dealer_id}/vehicles")
    public ResponseEntity<String> uploadVehicles(@RequestParam("file") MultipartFile fileListing, @PathVariable("dealer_id") String dealerId) {
        this.listingService.uploadVehicles(fileListing, dealerId);
        return ResponseEntity.ok("Data successfully uploaded");
    }
}
