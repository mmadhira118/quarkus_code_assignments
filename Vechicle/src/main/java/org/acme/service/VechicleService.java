package org.acme.service;


import io.smallrye.reactive.messaging.annotations.Blocking;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.TaxServiceResponse;
import org.acme.entity.Vehicle;
import org.acme.repository.VehicleRepository;
import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class VechicleService {

    @Inject
    VehicleRepository vehicleRepository;

    @Incoming("vehicle-data-in")
    @Blocking
    @Transactional
    public void process(TaxServiceResponse response) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(response.model);
        vehicle.setMake(response.make);
        vehicle.setLicensePlate(response.licensePlate);
        vehicle.setName(response.ownerName);
        vehicle.setMobileNumber(response.ownerMobileNumber);
        vehicle.setTaxAmount(response.taxAmount);
        vehicle.persist();
    }
}
