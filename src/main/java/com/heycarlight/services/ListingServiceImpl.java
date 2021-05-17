package com.heycarlight.services;

import com.heycarlight.controllers.VehicleRequest;
import com.heycarlight.entities.Vehicle;
import com.heycarlight.exceptions.CSVParsing;
import com.heycarlight.exceptions.NonExistingEntity;
import com.heycarlight.repositories.VehicleRepository;
import com.heycarlight.utils.CSVUtils;
import com.heycarlight.utils.VehicleConverter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service("DealerService")
public class ListingServiceImpl implements ListingService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DealerService dealerService;

    @Override
    public List<String> uploadVehicles(@NonNull List<VehicleRequest> listingRequest, @NonNull UUID dealerId) {
        if (this.dealerService.findById(dealerId).isEmpty()) {
            throw new NonExistingEntity("Dealer not found");
        }
        List<String> notUploaded = new LinkedList<>();
        Map<String, UUID> dealerMap = new HashMap<>();
        this.vehicleRepository.findByDealerId(dealerId).forEach(vehicle -> dealerMap.put(vehicle.getCode(), vehicle.getId()));
        listingRequest.forEach(vr -> {
            UUID existingId = dealerMap.get(vr.getCode());
            try{
                if (existingId != null) {
                    this.vehicleRepository.save(VehicleConverter.createFrom(vr, existingId, dealerId));
                } else {
                    this.vehicleRepository.save(VehicleConverter.createFrom(vr, dealerId));
                }
            } catch (RuntimeException e) {
                notUploaded.add(vr.getCode());
            }
        });
        return notUploaded;
    }

    @Override
    public List<String> uploadVehicles(@NonNull MultipartFile fileListing, @NonNull UUID dealerId) {
        try (CSVParser csvParser = CSVUtils.getCsvParser(fileListing)) {
            List<VehicleRequest> vehicleResponseDTOS = new LinkedList<>();
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                vehicleResponseDTOS.add(CSVUtils.getVehicleRequest(csvRecord));
            }
            return this.uploadVehicles(vehicleResponseDTOS, dealerId);
        } catch (IOException e) {
            throw new CSVParsing("Fail to parse file: " + fileListing.getName());
        }
    }
}
