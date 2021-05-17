package org.jsallari.services;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsallari.controllers.VehicleRequest;
import org.jsallari.entities.Vehicle;
import org.jsallari.exceptions.CSVParsing;
import org.jsallari.exceptions.NonExistingEntity;
import org.jsallari.repositories.VehicleRepository;
import org.jsallari.utils.CSVUtils;
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
    public List<String> uploadVehicles(@NonNull List<VehicleRequest> listingRequest, @NonNull String dealerId) {
        if (this.dealerService.findById(dealerId).isEmpty()) {
            throw new NonExistingEntity("Dealer not found");
        }
        Long idDealer = Long.valueOf(dealerId);
        List<String> notUploaded = new LinkedList<>();
        Map<String, Long> dealerMap = new HashMap<>();
        this.vehicleRepository.findByDealerId(idDealer).forEach(vehicle -> dealerMap.put(vehicle.getCode(), vehicle.getId()));
        listingRequest.forEach(vr -> {
            Long existingId = dealerMap.get(vr.getCode());
            try{
                if (vr.getCode().equals("code3")) {
                    throw new RuntimeException();
                }
                if (existingId != null) {
                    this.vehicleRepository.save(new Vehicle(existingId, vr.getCode(), vr.getModel(), vr.getMake(), vr.getYear(), vr.getKw(), vr.getColor(), vr.getPrice(), idDealer));
                } else {
                    this.vehicleRepository.save((new Vehicle(vr.getCode(), vr.getModel(), vr.getMake(), vr.getYear(), vr.getKw(), vr.getColor(), vr.getPrice(), idDealer)));
                }
            } catch (RuntimeException e) {
                notUploaded.add(vr.getCode());
            }
        });
        return notUploaded;
    }

    @Override
    public List<String> uploadVehicles(@NonNull MultipartFile fileListing, @NonNull String dealerId) {
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
