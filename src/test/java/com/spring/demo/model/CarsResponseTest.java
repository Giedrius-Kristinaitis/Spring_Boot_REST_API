package com.spring.demo.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests CarsResponse class
 */
public class CarsResponseTest {

    @Test
    public void testGetSetSize_shouldGetAndSetSize() {
        // setup
        CarsResponse carsResponse = new CarsResponse();

        int size = 1;

        // execute
        carsResponse.setSize(size);

        // assert
        assertEquals(size, carsResponse.getSize());
    }

    @Test
    public void testGetSetCars_shouldGetAndSetCars() {
        // setup
        CarsResponse carsResponse = new CarsResponse();

        Car one = new Car(0L, "not important model 1", 0, 0D);
        Car two = new Car(1L, "not important model 2", 0, 0D);

        List<Car> cars = Arrays.asList(one, two);

        // execute
        carsResponse.setCars(cars);

        // assert
        assertEquals(2, carsResponse.getCars().size());
    }
}
