package com.hercarlight.integration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class MediaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUploadListings() {

    }

    @Test
    public void testUploadListingFileError() {

    }

    @Test
    public void testUpdateAndCreate() {

    }

    @Test
    public void testUploadListingsWithFails() {

    }
}
