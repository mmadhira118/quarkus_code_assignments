package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Vehicle;

@ApplicationScoped
public class VehicleRepository implements PanacheRepository<Vehicle> {

}
