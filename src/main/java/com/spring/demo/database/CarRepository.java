package com.spring.demo.database;

import com.spring.demo.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD repository for cars
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long> { }
