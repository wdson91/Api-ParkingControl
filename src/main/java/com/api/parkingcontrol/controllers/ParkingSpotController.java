package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.CarModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.CarRepository;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingSpotController {
    final ParkingSpotService parkingSpotService;
    final CarRepository carRepository;

    public ParkingSpotController(ParkingSpotService parkingSpotService, CarRepository carRepository) {
        this.parkingSpotService = parkingSpotService;
        this.carRepository = carRepository;
    }

    @PostMapping
    public ParkingSpotModel saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
//        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
//        }
//        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
//        }
//        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
//        }
//        var parkingSpotModel = new ParkingSpotModel();
//        BeanUtils.copyProperties(parkingSpotDto,parkingSpotModel);
//        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
//        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        CarModel car = new CarModel();
        car.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        car.setBrandCar(parkingSpotDto.getBrandCar());
        car.setModelCar(parkingSpotDto.getModelCar());
        car.setColorCar(parkingSpotDto.getColorCar());
        car = parkingSpotService.saveCar(car);

        ParkingSpotModel parkingSpot = new ParkingSpotModel();
        parkingSpot.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpot.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpot.setApartment(parkingSpotDto.getApartment());
        parkingSpot.setBlock(parkingSpotDto.getBlock());
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        parkingSpot.setCar(car);
        return parkingSpotService.saveSpot(parkingSpot);
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.saveSpot(parkingSpotModel));
    }
}
