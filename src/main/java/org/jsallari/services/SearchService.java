package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    List<Vehicle> findBy(String model, String make, String year, String color);
    List<Vehicle> findAll();
}
