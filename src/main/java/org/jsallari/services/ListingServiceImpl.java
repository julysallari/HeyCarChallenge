package org.jsallari.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsallari.entities.Vehicle;
import org.jsallari.repositories.VehicleRepository;
import org.jsallari.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

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
            Vehicle vehicle = null;
            for (CSVRecord csvRecord : csvRecords) {
                vehicle = CSVUtils.getVehicleRecord(csvRecord);
                vehicle.setDealerId(Long.valueOf(dealerId));
                vehicles.add(vehicle);
            }
            vehicleRepository.saveAll(vehicles);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
