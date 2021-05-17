package com.heycarlight.controllers;

import com.heycarlight.responses.UploadResponse;
import com.heycarlight.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class MediaController {

    @Autowired
    private ListingService listingService;

    @PostMapping(value = "/upload_csv/{dealer_id}/vehicles")
    public ResponseEntity<UploadResponse> uploadVehicles(@RequestParam("file") MultipartFile fileListing, @PathVariable("dealer_id") String dealerId) {
        List<String> notUploaded = this.listingService.uploadVehicles(fileListing, UUID.fromString(dealerId));
        if (notUploaded.isEmpty()) {
            return ResponseEntity.ok(new UploadResponse("Data successfully uploaded"));
        }
        return ResponseEntity.ok(new UploadResponse("Some items could not be uploaded", notUploaded));
    }
}
