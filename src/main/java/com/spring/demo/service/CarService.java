package com.spring.demo.service;

import com.spring.demo.database.CarRepository;
import com.spring.demo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Performs CRUD operations with cars
 */
@Service
public class CarService {

    // crud car repo
    private CarRepository cars;

    /**
     * Default class constructor
     * @param cars car repository
     */
    @Autowired
    public CarService(CarRepository cars) {
        this.cars = cars;
    }

    /**
     * Gets a single car by it's id
     * @param id id of the car
     * @return car if it exists, null if it doesn't
     */
    public Car getCar(Long id) {
        Optional<Car> returned = cars.findById(id);

        if (!returned.isPresent()) {
            return null;
        }

        return returned.get();
    }

    /**
     * Gets all cars
     * @return list with cars, empty list if no cars are present
     */
    public List<Car> getCars() {
        return (List<Car>) cars.findAll();
    }

    /**
     * Adds a single car to the database
     * @param car car to insert
     * @return inserted car, null if it already exists and new one cannot be inserted
     */
    public Car addCar(Car car) {
        boolean exists = cars.existsById(car.getId());

        if (exists) {
            return null;
        }

        return cars.save(car);
    }

    /**
     * Updates a single car
     * @param id id of the car
     * @param car car to put to the database
     * @return updated car, null if no cars match the given id
     */
    public Car updateCar(Long id, Car car) {
        boolean exists = cars.existsById(id);

        if (!exists) {
            return null;
        }

        car.setId(id);

        return cars.save(car);
    }

    /**
     * Deletes a single car from the database
     * @param id id of the car
     * @return deleted car, null if a car with the given id doesn't exist
     */
    public Car deleteCar(Long id) {
        boolean exists = cars.existsById(id);

        if (!exists) {
            return null;
        }

        Car existing = cars.findById(id).get();

        cars.deleteById(id);

        return existing;
    }
}
