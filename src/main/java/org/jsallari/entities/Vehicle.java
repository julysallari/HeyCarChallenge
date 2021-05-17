package org.jsallari.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

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

    public Vehicle(@Nullable Long id,
                   @NotNull String code,
                   @NotNull String model,
                   @NotNull String make,
                   @NotNull Integer year,
                   @NotNull Integer kw,
                   @NotNull String color,
                   @NotNull Double price,
                   @NotNull Long dealerId) {
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

    public Vehicle(@NotNull String code,
                   @NotNull String model,
                   @NotNull String make,
                   @NotNull Integer year,
                   @NotNull Integer kw,
                   @NotNull String color,
                   @NotNull Double price,
                   @NotNull Long dealerId) {
        this(null, code, model, make, year, kw, color, price, dealerId);
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

    @Override
    public boolean equals(Object obj) {
        if(obj != null && this.getClass().equals(obj.getClass())) {
            Vehicle other = (Vehicle) obj;
            return this.dealerId.equals(other.dealerId) &&
                    this.code.equals(other.code) &&
                    this.model.equals(other.model) &&
                    this.make.equals(other.make) &&
                    this.year.equals(other.year) &&
                    this.kw.equals(other.kw) &&
                    this.color.equals(other.color) &&
                    this.price.equals(other.price);
        }
        return false;
    }
}

