package com.spring.demo.controller;

import com.spring.demo.model.Car;
import com.spring.demo.model.CarsResponse;
import com.spring.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Handles CRUD requests for cars
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    // car service used to perform crud operations on cars
    private CarService carService;

    /**
     * Default class constructor
     * @param carService autowired car service
     */
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Gets all cars
     * @return response entity with CarsResponse as it's body
     */
    @GetMapping
    public ResponseEntity<CarsResponse> getAllCars() {
        List<Car> cars = carService.getCars();

        CarsResponse response = new CarsResponse(cars != null ? cars.size() : 0, cars);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Gets a single car with the specified id
     * @param id car id
     * @return response entity with the Car as it's body
     */
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        Car car = carService.getCar(id);

        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    /**
     * Handles insert car request
     * @param car car to be inserted
     * @return newly inserted car
     */
    @PostMapping
    public ResponseEntity<Car> addCar(@Valid @RequestBody Car car) {
        Car insertedCar = carService.addCar(car);

        if (insertedCar == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(insertedCar, HttpStatus.OK);
    }

    /**
     * Handles car update request
     * @param id id of the car that will be updated
     * @param car updated car
     * @return updated car
     */
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        Car updatedCar = carService.updateCar(id, car);

        if (updatedCar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    /**
     * Handles delete car request
     * @param id id of the car that will be deleted
     * @return deleted car
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable Long id) {
        Car deletedCar = carService.deleteCar(id);

        if (deletedCar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedCar, HttpStatus.OK);
    }
}
