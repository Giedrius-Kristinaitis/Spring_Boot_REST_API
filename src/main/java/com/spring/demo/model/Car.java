package com.spring.demo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * Car model class
 */
@SuppressWarnings("unused")
public class Car {

    // id in the database
    private Long id;

    // model name
    @NotBlank(message = "Model cannot be blank string")
    private String model;

    // production year
    @Min(value = 1990, message = "Production year must be greater than 1990")
    private Integer year;

    // price (obviously)
    @PositiveOrZero(message = "Price must be a non-negative number")
    private Double price;

    /**
     * Default no-parameter constructor
     */
    public Car() {}

    /**
     * Constructor with parameters
     * @param id id of the car
     * @param model model of the car
     * @param year production year
     * @param price price of the car
     */
    public Car(Long id, String model, Integer year, Double price) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    /**
     * Gets the database id of the car
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets database id value
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the model name of the car
     * @return model name
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model name of the car
     * @param model new model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the production year of the car
     * @return production year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the production year
     * @param year new production year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the price of the car
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the car
     * @param price new price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
