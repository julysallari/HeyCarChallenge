package com.heycarlight.api;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heycarlight.entities.Vehicle;
import com.heycarlight.services.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchService searchService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Vehicle> vehicleList = new ArrayList<>();
    private Map<String, Object> fieldsParam = new HashMap<>();

    @BeforeEach
    public void setUp() throws IOException {
        fieldsParam = new HashMap<>();
        fieldsParam.put("model", "model1");
        fieldsParam.put("make", "make1");
        fieldsParam.put("year", "2021");
        fieldsParam.put("color", "color1");

        vehicleList = this.objectMapper.readValue(new String(Files.readAllBytes(new ClassPathResource("threeListings.json").getFile().toPath())),
                new TypeReference<>(){});
    }

    @Test
    public void testSearchAllFields() throws Exception {
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(List.of(vehicleList.get(0)));
        this.mvc.perform(get("/search")
                .param("model", "model1")
                .param("make", "make1")
                .param("year", "2021")
                .param("color", "color1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].code").value("code1"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchMissingModel() throws Exception {
        fieldsParam.put("model", null);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(List.of(vehicleList.get(0)));
        this.mvc.perform(get("/search")
                .param("make", "make1")
                .param("year", "2021")
                .param("color", "color1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].code").value("code1"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchMissingMake() throws Exception {
        fieldsParam.put("make", null);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(List.of(vehicleList.get(0)));
        this.mvc.perform(get("/search")
                .param("model", "model1")
                .param("year", "2021")
                .param("color", "color1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].code").value("code1"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchMissingYear() throws Exception {
        fieldsParam.put("year", null);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(List.of(vehicleList.get(1)));
        this.mvc.perform(get("/search")
                .param("model", "model1")
                .param("make", "make1")
                .param("color", "color1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].code").value("code2"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchMissingColor() throws Exception {
        fieldsParam.put("color", null);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(List.of(vehicleList.get(0)));
        this.mvc.perform(get("/search")
                .param("model", "model1")
                .param("make", "make1")
                .param("year", "2021")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].code").value("code1"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchEmptyFields() throws Exception {
        fieldsParam.put("model", null);
        fieldsParam.put("make", null);
        fieldsParam.put("year", null);
        fieldsParam.put("color", null);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(vehicleList);
        this.mvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].code").value("code1"))
                .andExpect(jsonPath("$[1].code").value("code2"))
                .andExpect(jsonPath("$[2].code").value("code3"));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }

    @Test
    public void testSearchByYear() throws Exception {
        fieldsParam.put("model", null);
        fieldsParam.put("make", null);
        fieldsParam.put("year", "2023");
        fieldsParam.put("color", null);
        vehicleList.remove(0);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(vehicleList);
        this.mvc.perform(get("/search")
                .param("year", "2023")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].code").value("code2"))
                .andExpect(jsonPath("$[1].code").value("code3"));

        verify(searchService, times(1)).findByFields(Mockito.any());
    }

    @Test
    public void testSearchReturnEmpty() throws Exception {
        fieldsParam.put("make", null);
        fieldsParam.put("year", null);
        fieldsParam.put("color", null);
        vehicleList.remove(0);
        Mockito.when(searchService.findByFields(fieldsParam)).thenReturn(new ArrayList<>());
        this.mvc.perform(get("/search")
                .param("model", "model1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(searchService, times(1)).findByFields(fieldsParam);
    }
}
