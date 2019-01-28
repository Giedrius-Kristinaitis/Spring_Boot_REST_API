package com.spring.demo.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests Car model class
 */
public class CarTest {

    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGetSetId_shouldSetAndReturnId() {
        // setup
        Car car = new Car(0L, "not important model", 0, 0D);
        Long newId = 1L;

        // execute
        car.setId(newId);

        // assert
        assertEquals(newId, car.getId());
    }

    @Test
    public void testGetSetModel_shouldSetAndReturnModel() {
        // setup
        Car car = new Car(0L, "initial not important model", 0, 0D);
        String newModel = "Any car model";

        // execute
        car.setModel(newModel);

        // assert
        assertEquals(newModel, car.getModel());
    }

    @Test
    public void testGetSetYear_shouldSetAndReturnYear() {
        // setup
        Car car = new Car(0L, "not important model", 0, 0D);
        Integer newYear = 1;

        // execute
        car.setYear(newYear);

        // assert
        assertEquals(newYear, car.getYear());
    }

    @Test
    public void testGetSetPrice_shouldSetAndReturnPrice() {
        // setup
        Car car = new Car(0L, "not important model", 0, 0D);
        Double newPrice = 1D;

        // execute
        car.setPrice(newPrice);

        // assert
        assertEquals(newPrice, car.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPriceNegative_shouldThrowIllegalArgumentException() {
        // setup
        Car car = new Car(0L, "not important model", 0, 0D);
        Double newPrice = -5D;

        // execute
        car.setPrice(newPrice);
    }

    @Test
    public void testValidationValidValues() {
        // setup
        Car car = new Car(0L, "Valid model value", 1990, 0D);

        // execute
        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        // assert
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationBlankModelName_shouldFailToValidate() {
        // setup
        Car car = new Car(0L, "", 1990, 0D);

        // execute
        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        // assert
        assertEquals("Model name cannot be blank string",
                violations.iterator().next().getMessage());
    }

    @Test
    public void testValidationYearLessThan1990_shouldFailToValidate() {
        // setup
        Car car = new Car(0L, "Valid model", 1000, 0D);

        // execute
        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        // assert
        assertEquals("Production year must be greater than 1990",
                violations.iterator().next().getMessage());
    }

    @Test
    public void testValidationNegativePrice_shouldFailToValidate() {
        // setup
        Car car = new Car(0L, "Valid model", 1990, -1D);

        // execute
        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        // assert
        assertEquals("Price must be a non-negative number",
                violations.iterator().next().getMessage());
    }
}
