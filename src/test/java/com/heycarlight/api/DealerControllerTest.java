package com.heycarlight.api;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heycarlight.entities.Dealer;
import com.heycarlight.exceptions.NonExistingEntity;
import com.heycarlight.requests.VehicleRequest;
import com.heycarlight.services.DealerService;
import com.heycarlight.services.ListingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DealerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealerService dealerService;

    @MockBean
    private ListingService listingService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<VehicleRequest> vehicleList = new ArrayList<>();

    private UUID dealerId = UUID.randomUUID();

    private final String okMsg = "Data successfully uploaded";

    @Test
    public void testCreateDealerOk() throws Exception {
        Dealer dealer = new Dealer("john");
        when(this.dealerService.addDealer(dealer)).thenReturn(dealer);
        this.mvc.perform(post("/dealers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dealer.toString()))
                .andExpect(status().isCreated());

        verify(this.dealerService, times(1)).addDealer(any(Dealer.class));
    }

    @Test
    public void testUploadListingsOk() throws Exception {
        when(this.listingService.uploadVehicles(anyList(), any(UUID.class))).thenReturn(new ArrayList<>());
        this.mvc.perform(post("/dealers/" + dealerId + "/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(okMsg))
                .andExpect(jsonPath("$.failed").doesNotExist());

        verify(this.listingService, times(1)).uploadVehicles(anyList(), any(UUID.class));
    }

    @Test
    public void testUploadSingleListingOk() throws Exception {
        when(this.listingService.uploadVehicles(anyList(), any(UUID.class))).thenReturn(new ArrayList<>());
        VehicleRequest vr = new VehicleRequest("myCode", "myModel", "myMake", 2021, 100000, "red", 1000.0);
        this.mvc.perform(post("/dealers/" + dealerId + "/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(vr))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(okMsg))
                .andExpect(jsonPath("$.failed").doesNotExist());

        verify(this.listingService, times(1)).uploadVehicles(anyList(), any(UUID.class));
    }

    @Test
    public void testUploadEmpty() throws Exception {
        when(this.listingService.uploadVehicles(anyList(), any(UUID.class))).thenReturn(new ArrayList<>());
        this.mvc.perform(post("/dealers/" + dealerId + "/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        verify(this.listingService, times(0)).uploadVehicles(anyList(), any(UUID.class));
    }

    @Test
    public void testUploadListingJsonError() throws Exception {
        String errorMsg = "Dealer not found";
        when(this.listingService.uploadVehicles(anyList(), any(UUID.class))).thenThrow(new NonExistingEntity(errorMsg));
        this.mvc.perform(post("/dealers/" + dealerId + "/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleList)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMsg));

        verify(this.listingService, times(1)).uploadVehicles(anyList(), any(UUID.class));
    }

    @Test
    public void testUploadListingsWithFails() throws Exception {
        UUID failedId = UUID.randomUUID();
        List<String> failedIds = List.of(failedId.toString());
        String errorMsg = "Some items could not be uploaded";
        when(this.listingService.uploadVehicles(anyList(), any(UUID.class))).thenReturn(failedIds);

        this.mvc.perform(post("/dealers/" + dealerId + "/vehicle_listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(errorMsg))
                .andExpect(jsonPath("$.failed.size()").value(1))
                .andExpect(jsonPath("$.failed.[0]").value(failedId.toString()));

        verify(this.listingService, times(1)).uploadVehicles(anyList(), any(UUID.class));
    }
}
