package com.asgr.minderest.repository;

import com.asgr.minderest.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findById(String id);

}
