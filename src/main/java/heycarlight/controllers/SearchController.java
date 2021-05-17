package heycarlight.controllers;

import heycarlight.entities.Vehicle;
import heycarlight.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getVehicles(@RequestParam(required = false) String model,
                                                     @RequestParam(required = false) String make,
                                                     @RequestParam(required = false) String year,
                                                     @RequestParam(required = false) String color) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("model", model);
        fields.put("make", make);
        fields.put("year", year);
        fields.put("color", color);
        return ResponseEntity.ok(this.searchService.findByFields(fields));
    }
}
