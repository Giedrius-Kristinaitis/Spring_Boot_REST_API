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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mvc.perform(get("/api/cars/0").contentType(MediaType.APPLICATION_JSON))
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
        mvc.perform(get("/api/cars/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCars_shouldReturnAllCars() throws Exception {
        // setup (1990 is the minimum allowed year)
        Car one = new Car(0L, "Not important model 1", 1990, 0D);
        Car two = new Car(1L, "Not important model 2", 1990, 0D);

        given(service.getCars()).willReturn(Arrays.asList(one, two));

        // execute and assert
        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
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
        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(0)));
    }
}
