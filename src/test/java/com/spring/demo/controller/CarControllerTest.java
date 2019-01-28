package com.spring.demo.controller;

import com.spring.demo.model.Car;
import com.spring.demo.service.CarService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;

    @Test
    public void testGetCar_shouldReturnCar() throws Exception {
        // setup (1990 is the minimum allowed year)
        Car one = new Car(0L, "Not important model", 1990, 0D);

        given(service.getCar(0L)).willReturn(one);

        // execute and assert
        mvc.perform(get("/api/cars/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is(one.getModel())))
                .andExpect(jsonPath("$.year", is(one.getYear())))
                .andExpect(jsonPath("$.price", is(one.getPrice())));
    }

    @Test
    public void testGetCar_shouldReturnStatusNotFound() throws Exception {
        // setup
        given(service.getCar(0L)).willReturn(null);

        // execute and assert
        mvc.perform(get("/api/cars/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCars_shouldReturnAllCars() throws Exception {
        // setup (1990 is the minimum allowed year)
        Car one = new Car(0L, "Not important model 1", 1990, 0D);
        Car two = new Car(1L, "Not important model 2", 1990, 0D);

        given(service.getCars()).willReturn(Arrays.asList(one, two));

        // execute and assert
        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.cars[0].model", is(one.getModel())))
                .andExpect(jsonPath("$.cars[1].model", is(two.getModel())));
    }

    @Test
    public void testGetAllCars_shouldReturnSizeZero() throws Exception {
        // setup
        given(service.getCars()).willReturn(null);

        // execute and assert
        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(0)));
    }

    @Test
    public void testAddCar_shouldReturnAddedCar() throws Exception {
        // setup
        Car car = new Car(0L, "Not important model", 1990, 0D);

        given(service.addCar(any(Car.class))).willReturn(car);

        // execute and assert
        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"model\":\"Not important model\", \"year\": 1990, \"price\": 0}")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is(car.getModel())));
    }

    @Test
    public void testAddCar_shouldFailValidationAndReturnBadRequest() throws Exception {
        // execute and assert
        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"model\": \"\"}")
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCar_shouldReturnConflictBecauseCarWithSameModelNameAlreadyExists() throws Exception {
        // setup
        given(service.addCar(any(Car.class))).willReturn(null);

        // execute and assert
        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"model\": \"Important model\", \"year\": 1990, \"price\": 0}")
                .characterEncoding("utf-8"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateCar_shouldReturnUpdatedCar() throws Exception {
        // setup
        Car car = new Car(1L, "Not important model", 1990, 0D);

        given(service.updateCar(eq(1L), any(Car.class))).willReturn(car);

        // execute and assert
        mvc.perform(put("/api/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"model\": \"Not important model\", \"year\": 1990, \"price\": 0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year", is(car.getYear())));
    }

    @Test
    public void testUpdateCar_shouldReturnNotFound() throws Exception {
        // setup
        given(service.updateCar(eq(1L), any(Car.class))).willReturn(null);

        // execute and assert
        mvc.perform(put("/api/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\"model\": \"Not important model\", \"year\": 1990, \"price\": 0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCar_shouldReturnDeletedCar() throws Exception {
        // setup
        Car car = new Car(1L, "Not important model", 1990, 0D);

        given(service.deleteCar(1L)).willReturn(car);

        // execute and assert
        mvc.perform(delete("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(car.getPrice())));
    }

    @Test
    public void testDeleteCar_shouldReturnNotFound() throws Exception {
        // setup
        given(service.deleteCar(1L)).willReturn(null);

        // execute and assert
        mvc.perform(delete("/api/cars/1"))
                .andExpect(status().isNotFound());
    }
}
