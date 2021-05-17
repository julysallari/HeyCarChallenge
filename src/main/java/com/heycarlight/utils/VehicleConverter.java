package com.heycarlight.utils;

import com.heycarlight.controllers.VehicleRequest;
import com.heycarlight.entities.Vehicle;

import java.util.UUID;

public class VehicleConverter {

    public static Vehicle createFrom(VehicleRequest vr, UUID id, UUID dealerId) {
        return new Vehicle(id, vr.getCode(), vr.getModel(), vr.getMake(), vr.getYear(), vr.getKw(), vr.getColor(), vr.getPrice(), dealerId);
    }

    public static Vehicle createFrom(VehicleRequest vr, UUID dealerId) {
        return createFrom(vr, null, dealerId);
    }
}
