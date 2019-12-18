package com.asgr.minderest.service;

import com.asgr.minderest.model.Product;
import com.asgr.minderest.repository.ProductRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@NoArgsConstructor
@Component
@Accessors(fluent = true)
public class ProductService extends BaseCrudService<Product, ProductRepository> implements Serializable {

    private final long serialVersionUID = 0L;

    @Autowired
    @Getter
    ProductRepository repository;
}
