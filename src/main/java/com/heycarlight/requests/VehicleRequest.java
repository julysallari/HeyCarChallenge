package com.heycarlight.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class VehicleRequest {
    private final String code;
    private final String model;
    private final String make;
    private final Integer year;
    private final Integer kw;
    private final String color;
    private final Double price;

    @JsonCreator
    public VehicleRequest(@JsonProperty(value = "code", required = true) @NotNull String code,
                              @JsonProperty(value = "model", required = true) @NotNull String model,
                              @JsonProperty(value = "make", required = true) @NotNull String make,
                              @JsonProperty(value = "year", required = true) @NotNull Integer year,
                              @JsonProperty(value = "kw", required = true) @NotNull Integer kw,
                              @JsonProperty(value = "color", required = true) @NotNull String color,
                              @JsonProperty(value = "price", required = true) @NotNull Double price) {
        this.code = code;
        this.model = model;
        this.make = make;
        this.year = year;
        this.kw = kw;
        this.color = color;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getKw() {
        return kw;
    }

    public String getColor() {
        return color;
    }

    public Double getPrice() {
        return price;
    }
}
