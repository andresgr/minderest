package com.asgr.minderest.repository;

import com.asgr.minderest.model.Client;
import com.asgr.minderest.model.ClientProduct;
import com.asgr.minderest.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientProductRepository extends CrudRepository<ClientProduct, Long> {

    ClientProduct findByClient(Client client);

    List<ClientProduct> findByProduct(Product product);

    ClientProduct findByClientAndClientProductName(Client client, String clientProductName);

}
