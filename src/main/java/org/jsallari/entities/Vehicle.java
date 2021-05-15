package org.jsallari.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

import javax.validation.constraints.NotNull;

@Entity
public class Vehicle extends Listing {

    private Long dealerId;
    private String code;
    private String model;
    private String make;
    private Integer year;
    private Integer kw;
    private String color;
    private Double price;

    private Vehicle() {
        super();
    }

    @JsonCreator
    public Vehicle(@JsonProperty(value = "code", required = true) @NotNull String code,
                   String model, String make, Integer year, Integer kw, String color, Double price) {
        super();
        this.code = code;
        this.model = model;
        this.make = make;
        this.year = year;
        this.kw = kw;
        this.color = color;
        this.price = price;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getDealerId() {
        return dealerId;
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

