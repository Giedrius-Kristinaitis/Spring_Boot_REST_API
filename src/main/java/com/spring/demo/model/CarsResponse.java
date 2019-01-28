package com.spring.demo.model;

import java.util.List;

/**
 * Response used when returning all cars
 */
@SuppressWarnings("unused")
public class CarsResponse {

    // number of cars
    private int size;

    // actual cars
    private List<Car> cars;

    /**
     * Default no-arguments constructor
     */
    public CarsResponse() {}

    /**
     * Constructor with arguments
     * @param size number of cars
     * @param cars actual cars
     */
    public CarsResponse(int size, List<Car> cars) {
        this.size = size;
        this.cars = cars;
    }

    /**
     * Gets the number of cars
     * @return num of cars
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the number of cars
     * @param size new number of cars
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets cars
     * @return cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets cars
     * @param cars new cars
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
