package com.spring.demo.service;

import com.spring.demo.database.CarRepository;
import com.spring.demo.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService service;

    @MockBean
    private CarRepository cars;

    @Test
    public void testGetCarById_shouldReturnCar() {
        // setup
        Long id = 1L;
        Car car = new Car(id, "Not important model", 0, 0D);

        given(cars.findById(id)).willReturn(Optional.ofNullable(car));

        // execute
        Car returned = service.getCar(id);

        // assert
        assertEquals(id, returned.getId());
        assertEquals(car.getModel(), returned.getModel());
        assertEquals(car.getYear(), returned.getYear());
        assertEquals(car.getPrice(), returned.getPrice());
    }

    @Test
    public void testGetCarById_shouldReturnNull() {
        // setup
        Long id = 1L;

        given(cars.findById(id)).willReturn(Optional.ofNullable(null));

        // execute
        Car returned = service.getCar(id);

        // assert
        assertEquals(null, returned);
    }

    @Test
    public void testGetCars_shouldReturnAllCars() {
        // setup
        Car one = new Car(0L, "Not important model 1", 0, 0D);
        Car two = new Car(1L, "Not important model 2", 0, 0D);

        given(cars.findAll()).willReturn(Arrays.asList(one, two));

        // execute
        Iterable<Car> cars = service.getCars();

        // assert
        assertEquals(2, ((List<Car>) cars).size());
        assertEquals(one.getModel(), ((List<Car>) cars).get(0).getModel());
        assertEquals(two.getModel(), ((List<Car>) cars).get(1).getModel());
    }

    @Test
    public void testAddCar_shouldReturnInsertedCar() {
        // setup
        Car car = new Car(1L, "Not important model", 0, 0D);

        given(cars.save(car)).willReturn(car);

        // execute
        Car returned = service.addCar(car);

        // assert
        assertEquals(car.getId(), returned.getId());
    }

    @Test
    public void testAddCar_shouldReturnNull() {
        // setup
        Car car = new Car(1L, "Not important model", 0, 0D);

        given(cars.existsById(car.getId())).willReturn(true);

        // execute
        Car inserted = service.addCar(car);

        // assert
        assertEquals(null, inserted);
    }

    @Test
    public void testUpdateCar_shouldReturnUpdatedCar() {
        // setup
        Car car = new Car(1L, "Not Important model", 0, 0D);

        given(cars.existsById(1L)).willReturn(true);
        given(cars.save(car)).willReturn(car);

        // execute
        Car updated = service.updateCar(car.getId(), car);

        // assert
        assertEquals(car.getModel(), updated.getModel());
    }

    @Test
    public void testUpdateCar_shouldReturnNull() {
        // setup
        Long id = 1L;

        given(cars.existsById(id)).willReturn(false);

        // execute
        Car updated = service.updateCar(id, null);

        // assert
        assertEquals(null, updated);
    }

    @Test
    public void testDeleteCar_shouldReturnDeletedCar() {
        // setup
        Long id = 1L;
        Car car = new Car(id, "Not important model", 0, 0D);

        given(cars.existsById(id)).willReturn(true);
        given(cars.findById(id)).willReturn(Optional.ofNullable(car));

        // execute
        Car deleted = service.deleteCar(id);

        // assert
        assertEquals(id, deleted.getId());
    }

    @Test
    public void testDeleteCar_shouldReturnNull() {
        // setup
        given(cars.existsById(1L)).willReturn(false);

        // execute
        Car deleted = service.deleteCar(1L);

        // assert
        assertEquals(null, deleted);
    }
}
