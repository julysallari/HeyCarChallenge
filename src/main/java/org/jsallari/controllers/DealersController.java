package org.jsallari.controllers;

import org.jsallari.entities.Vehicle;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealers")
public class DealersController {

    @PostMapping(value = "/{dealer_id}/vehicle_listings/upload", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadVehicleJson(@RequestBody List<Vehicle> listing) {
        return ResponseEntity.ok("Data successfully uploaded");
    }
}
