package org.jsallari.services;

import org.jsallari.entities.Vehicle;
import org.jsallari.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DealerService")
public class DealerServiceImpl implements DealerService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void uploadListing(List<Vehicle> listing, String dealerId) {
        listing.forEach(vehicle -> vehicle.setDealerId(Long.valueOf(dealerId)));
        vehicleRepository.saveAll(listing);
    }
}
