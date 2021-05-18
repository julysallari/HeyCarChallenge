package com.heycarlight.api;
import com.heycarlight.exceptions.CSVParsing;
import com.heycarlight.exceptions.NonExistingEntity;
import com.heycarlight.services.DealerService;
import com.heycarlight.services.ListingService;
import com.heycarlight.utils.MultimediaFileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.runner.RunWith;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MediaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealerService dealerService;

    @MockBean
    private ListingService listingService;

    private final UUID dealerId = UUID.randomUUID();
    private final String okMsg = "Data successfully uploaded";

    @Test
    public void testUploadListingsOk() throws Exception {
        when(this.listingService.uploadVehicles(any(MultipartFile.class), any(UUID.class))).thenReturn(new ArrayList<>());
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "twoListings.csv");

        this.mvc.perform(multipart("/upload_csv/"+dealerId).file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(okMsg))
                .andExpect(jsonPath("$.failed").doesNotExist());

        verify(this.listingService, times(1)).uploadVehicles(any(MultipartFile.class), any(UUID.class));
    }

    @Test
    public void testUploadListingsEmptyFile() throws Exception {
        when(this.listingService.uploadVehicles(any(MultipartFile.class), any())).thenReturn(new ArrayList<>());
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("missingFile", "twoListings.csv");

        this.mvc.perform(multipart("/upload_csv/"+dealerId).file(mockMultipartFile))
                .andExpect(status().isBadRequest());

        verify(this.listingService, times(0)).uploadVehicles(any(MultipartFile.class), any(UUID.class));
    }

    @Test
    public void testUploadListingFileError() throws Exception {
        String errorMsg = "Throws error";
        when(this.listingService.uploadVehicles(any(MultipartFile.class), any(UUID.class))).thenThrow(new CSVParsing(errorMsg));
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "errorListingsFile.csv");
        this.mvc.perform(multipart("/upload_csv/"+dealerId).file(mockMultipartFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(errorMsg));

        verify(this.listingService, times(1)).uploadVehicles(any(MultipartFile.class), any(UUID.class));
    }

    @Test
    public void testUploadListingFileUnexistentDealer() throws Exception {
        String errorMsg = "Dealer not found";
        when(this.listingService.uploadVehicles(any(MultipartFile.class), any(UUID.class))).thenThrow(new NonExistingEntity(errorMsg));
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "errorListingsFile.csv");
        this.mvc.perform(multipart("/upload_csv/"+dealerId).file(mockMultipartFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMsg));

        verify(this.listingService, times(1)).uploadVehicles(any(MultipartFile.class), any(UUID.class));
    }

    @Test
    public void testUploadListingsWithFails() throws Exception {
        UUID failedId = UUID.randomUUID();
        List<String> failedIds = List.of(failedId.toString());
        String errorMsg = "Some items could not be uploaded";
        when(this.listingService.uploadVehicles(any(MultipartFile.class), any(UUID.class))).thenReturn(failedIds);
        MockMultipartFile mockMultipartFile = MultimediaFileUtils.getMockedFile("file", "twoListings.csv");

        this.mvc.perform(multipart("/upload_csv/"+dealerId).file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(errorMsg))
                .andExpect(jsonPath("$.failed.size()").value(1))
                .andExpect(jsonPath("$.failed.[0]").value(failedId.toString()));

        verify(this.listingService, times(1)).uploadVehicles(any(MultipartFile.class), any(UUID.class));
    }
}
