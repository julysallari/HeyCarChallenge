package com.heycarlight.services;

import com.heycarlight.entities.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SearchService {

    List<Vehicle> findByFields(Map<String, Object> fields);
}
