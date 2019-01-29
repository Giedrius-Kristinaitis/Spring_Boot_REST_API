package com.spring.demo.database;

import com.spring.demo.model.Car;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD repository for cars
 */
public interface CarRepository extends CrudRepository<Car, Long> { }
