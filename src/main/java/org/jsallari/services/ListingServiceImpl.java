package org.jsallari.services;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsallari.entities.Listing;
import org.jsallari.entities.Vehicle;
import org.jsallari.repositories.VehicleRepository;
import org.jsallari.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("DealerService")
public class ListingServiceImpl implements ListingService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void uploadVehicles(List<Vehicle> listing, String dealerId) {
        listing.forEach(vehicle -> vehicle.setDealerId(Long.valueOf(dealerId)));
        vehicleRepository.saveAll(listing);
    }

    @Override
    public void uploadVehicles(MultipartFile fileListing, String dealerId) {
        try (CSVParser csvParser = CSVUtils.getCsvParser(fileListing)) {

            List<Vehicle> vehicles = new LinkedList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            Optional<Listing> optVehicle;
            Vehicle vehicle;
            for (CSVRecord csvRecord : csvRecords) {
                optVehicle = CSVUtils.getListingRecord(csvRecord, Vehicle.class);
                if (optVehicle.isEmpty()) {
                    throw new RuntimeException("fail to parse CSV file"); //TODO
                }
                vehicle = (Vehicle) optVehicle.get();
                vehicle.setDealerId(Long.valueOf(dealerId));
                vehicles.add(vehicle);
            }
            vehicleRepository.saveAll(vehicles);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
