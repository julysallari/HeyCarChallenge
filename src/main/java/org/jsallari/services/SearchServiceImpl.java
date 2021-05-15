package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.jsallari.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Lists;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findBy(@Nullable final String model, @Nullable String make, @Nullable String year, @Nullable String color) {
        return new LinkedList<>();
    }

    public List<Vehicle> findAll() {
        return Lists.newArrayList(this.vehicleRepository.findAll());
    }
}
