package com.heycarlight.unit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heycarlight.entities.Dealer;
import com.heycarlight.repositories.VehicleRepository;
import com.heycarlight.requests.VehicleRequest;
import com.heycarlight.services.DealerService;
import com.heycarlight.services.ListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListingServiceUnitTest {

    @MockBean
    private DealerService dealerService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<VehicleRequest> requests = new ArrayList<>();

    private ListingService listingService;
    private final Dealer dealer = new Dealer("john");
    private final UUID dealerId = UUID.randomUUID();

    @BeforeEach
    public void setUp() throws IOException {
        requests = this.objectMapper.readValue(new String(Files.readAllBytes(new ClassPathResource("threeListings.json").getFile().toPath())),
                new TypeReference<>(){});
    }

    @Test
    public void testFailedSomeUploads() {
        when(dealerService.findById(any(UUID.class))).thenReturn(Optional.of(dealer));
        when(vehicleRepository.save(any())).thenThrow(RuntimeException.class);

        listingService = new ListingService(dealerService, vehicleRepository);

        assertThat(this.listingService.uploadVehicles(requests, dealerId)).hasSize(3);
    }
}
