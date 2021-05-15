package org.jsallari.controllers;

import org.jsallari.entities.Vehicle;
import org.jsallari.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles(@RequestParam String model, @RequestParam String make, @RequestParam String year, @RequestParam String color) {
        return ResponseEntity.ok(this.searchService.findBy(model, make, year, color));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAll() {
        return ResponseEntity.ok(this.searchService.findAll());
    }
}
