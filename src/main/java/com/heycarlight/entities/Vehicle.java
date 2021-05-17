package com.heycarlight.entities;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Vehicle extends Listing {

    private UUID dealerId;
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

    public Vehicle(@Nullable UUID id,
                   @NotNull String code,
                   @NotNull String model,
                   @NotNull String make,
                   @NotNull Integer year,
                   @NotNull Integer kw,
                   @NotNull String color,
                   @NotNull Double price,
                   @NotNull UUID dealerId) {
        super(id);
        this.code = code;
        this.model = model;
        this.make = make;
        this.year = year;
        this.kw = kw;
        this.color = color;
        this.price = price;
        this.dealerId = dealerId;
    }

    public UUID getDealerId() {
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

