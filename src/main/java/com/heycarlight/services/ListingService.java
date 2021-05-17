package com.heycarlight.services;

import com.heycarlight.requests.VehicleRequest;
import com.heycarlight.exceptions.CSVParsing;
import com.heycarlight.exceptions.NonExistingEntity;
import com.heycarlight.repositories.VehicleRepository;
import com.heycarlight.utils.CSVUtils;
import com.heycarlight.entities.VehicleConverter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ListingService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DealerService dealerService;

    private static Logger LOGGER = LoggerFactory.getLogger(ListingService.class);

    public List<String> uploadVehicles(@NonNull List<VehicleRequest> listingRequest, @NonNull UUID dealerId) {
        if (this.dealerService.findById(dealerId).isEmpty()) {
            LOGGER.info("Upload listings: Dealer with id " + dealerId + " not found");
            throw new NonExistingEntity("Dealer not found");
        }
        List<String> notUploaded = new LinkedList<>();
        Map<String, UUID> dealerMap = new HashMap<>();
        this.vehicleRepository.findByDealerId(dealerId).forEach(vehicle -> dealerMap.put(vehicle.getCode(), vehicle.getId()));
        LOGGER.info("Uploading data for: " + dealerId);
        listingRequest.forEach(vr -> {
            UUID existingId = dealerMap.get(vr.getCode());
            try{
                if (existingId != null) {
                    this.vehicleRepository.save(VehicleConverter.createFrom(vr, existingId, dealerId));
                    LOGGER.info("Updated vehicle with id: " + existingId + " for " + dealerId);
                } else {
                    this.vehicleRepository.save(VehicleConverter.createFrom(vr, dealerId));
                    LOGGER.info("Created vehicle with code:" + vr.getCode() + " for " + dealerId);
                }
            } catch (RuntimeException e) {
                notUploaded.add(vr.getCode());
                LOGGER.error("Error creating/updating: Vehicle - " + dealerId + " - " + vr.getCode());
            }
        });
        LOGGER.info("Upload complete for: " + dealerId);
        return notUploaded;
    }

    public List<String> uploadVehicles(@NonNull MultipartFile fileListing, @NonNull UUID dealerId) {
        try (CSVParser csvParser = CSVUtils.getCsvParser(fileListing)) {
            List<VehicleRequest> vehicleResponseDTOS = new LinkedList<>();
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                vehicleResponseDTOS.add(CSVUtils.getVehicleRequest(csvRecord));
            }
            return this.uploadVehicles(vehicleResponseDTOS, dealerId);
        } catch (IOException e) {
            LOGGER.error("Error parsing CSV file " + fileListing.getName() + " for dealer " + dealerId);
            throw new CSVParsing("Fail to parse file: " + fileListing.getName());
        }
    }
}
