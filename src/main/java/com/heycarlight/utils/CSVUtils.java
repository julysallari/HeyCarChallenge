package com.heycarlight.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.heycarlight.controllers.VehicleRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CSVUtils {

    public static CSVParser getCsvParser(MultipartFile file) throws IOException {
        return new CSVParser(new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)),
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
    }

    public static VehicleRequest getVehicleRequest(CSVRecord csvRecord) {
        String make = csvRecord.get("make/model");
        int breakIndx = make.indexOf("/");
        String model = make.substring(breakIndx+1);
        make = make.substring(0, breakIndx);
        return new VehicleRequest(
                csvRecord.get("code"),
                make,
                model,
                Integer.valueOf(csvRecord.get("year")),
                Integer.valueOf(csvRecord.get("power-in-ps")),
                csvRecord.get("color"),
                Double.valueOf(csvRecord.get("price"))
        );
    }
}
