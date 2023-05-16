package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarModel, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);
}
