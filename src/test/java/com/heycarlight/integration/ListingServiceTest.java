package com.heycarlight.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heycarlight.entities.Dealer;
import com.heycarlight.entities.Vehicle;
import com.heycarlight.repositories.DealerRepository;
import com.heycarlight.repositories.VehicleRepository;
import com.heycarlight.requests.VehicleRequest;
import com.heycarlight.services.DealerService;
import com.heycarlight.services.ListingService;
import com.heycarlight.utils.MultimediaFileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ListingServiceTest {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DealerRepository dealerRepository;

    private DealerService dealerService;

    @Autowired
    private ObjectMapper objectMapper;

    private VehicleRequest vr4;
    private List<VehicleRequest> requests = new ArrayList<>();

    private UUID dealerId;
    private ListingService listingService;


    @BeforeEach
    public void setUp() throws IOException {
        dealerService = new DealerService(dealerRepository);
        vr4 = new VehicleRequest("4","model4", "make4",2040, 10002,"color4",4000.0);
        requests = this.objectMapper.readValue(new String(Files.readAllBytes(new ClassPathResource("threeListings.json").getFile().toPath())),
                new TypeReference<>(){});
    }

    @Test
    public void testUploadListingsOk() {
        dealerId = dealerService.addDealer(new Dealer("jonh")).getId();
        listingService = new ListingService(dealerService, vehicleRepository);
        assertThat(this.listingService.uploadVehicles(requests, dealerId)).isEmpty();
        List<Vehicle> vehicles = this.vehicleRepository.findByDealerId(dealerId);
        assertThat(vehicles).hasSize(requests.size());
        assertThat(vehicles).usingElementComparatorOnFields("code").isEqualTo(vehicles);
    }

    @Test
    public void testCreateAndUpdate() {
        VehicleRequest toUpdate = new VehicleRequest("code1", "model1", "make1", 2021, 10000, "color1", 10.0);
        List<VehicleRequest> newRequests = List.of(vr4, toUpdate);
        dealerId = dealerService.addDealer(new Dealer("jonh")).getId();

        listingService = new ListingService(dealerService, vehicleRepository);

        assertThat(this.listingService.uploadVehicles(requests, dealerId)).isEmpty();

        List<Vehicle> vehicles = this.vehicleRepository.findByDealerId(dealerId);
        assertThat(vehicles).hasSize(requests.size());
        assertThat(this.listingService.uploadVehicles(newRequests, dealerId)).isEmpty();

        vehicles = this.vehicleRepository.findByDealerId(dealerId);
        assertThat(vehicles).hasSize(requests.size()+1);
        assertThat(vehicles.get(0).getCode()).isEqualTo(toUpdate.getCode());
        assertThat(vehicles.get(0).getPrice()).isEqualTo(toUpdate.getPrice());
        assertThat(vehicles.stream().filter(vehicle -> vehicle.getCode().equals("code1"))).hasSize(1);
    }

    @Test
    public void testUploadNonExistingDealer() {
        listingService = new ListingService(dealerService, vehicleRepository);
        assertThatThrownBy(() -> listingService.uploadVehicles(requests, UUID.randomUUID()))
                .hasMessage("Dealer not found");

    }


    @Test
    public void testUploadCsvOk() throws IOException {
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "twoListings.csv");
        dealerId = dealerService.addDealer(new Dealer("jonh")).getId();

        listingService = new ListingService(dealerService, vehicleRepository);

        assertThat(this.listingService.uploadVehicles(mockMultipartFile, dealerId)).isEmpty();

        List<Vehicle> vehicles = this.vehicleRepository.findByDealerId(dealerId);
        assertThat(vehicles).hasSize(2);
    }

    @Test
    public void testUploadCsvParseFails() throws IOException {
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "errorListingsFile.csv");
        listingService = new ListingService(dealerService, vehicleRepository);
        assertThatThrownBy(() -> listingService.uploadVehicles(mockMultipartFile, UUID.randomUUID()))
                .hasMessage("Fail to parse file: file");
    }
}
