package com.asgr.minderest.service;

import com.asgr.minderest.model.Client;
import com.asgr.minderest.model.ClientProduct;
import com.asgr.minderest.model.Product;
import com.asgr.minderest.repository.ClientProductRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@Component
@Accessors(fluent = true)
public class ClientProductService extends BaseCrudService<ClientProduct, ClientProductRepository> implements Serializable {

    private final long serialVersionUID = 0L;

    @Autowired
    @Getter
    ClientProductRepository repository;

    @Autowired
    @Getter
    ProductService productService;

    public ClientProduct createProduct(
            @NotNull @Valid Client client,
            @NotNull @NotBlank @Size(min = ClientProduct.MIN_PRODUCT_NAME_LENGTH,
                    max = ClientProduct.MAX_PRODUCT_NAME_LENGTH) String productName) {
        Preconditions.checkNotNull(client);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(productName), "productName cannot be null or empty");

        log.debug("Creating product '{}' for client '{}'", productName, client.getClientName());
        Product product = new Product();
        productService.create(product);

        ClientProduct clientProduct = new ClientProduct(client, product, productName);
        try {
            create(clientProduct);
        } catch (DataIntegrityViolationException ex) {
            throw new ClientProductAlreadyExists(client.getClientId(), productName, ex);
        }
        return clientProduct;
    }

    public List<ClientProduct> findOtherClientProducts(@NotNull @Valid ClientProduct clientProduct) {
        Preconditions.checkNotNull(clientProduct);

        log.debug("Finding corresponding client '{}' products for product '{}'",
                clientProduct.getClientName(), clientProduct.getClientProductName());

        return repository.findByProduct(clientProduct.getProduct()).stream()
                .filter(product -> !clientProduct.equals(product))
                .collect(Collectors.toList());
    }

    public ClientProduct setClientProduct(
            @NotNull @Valid ClientProduct clientProduct, @NotNull @Valid Product product) {
        Preconditions.checkNotNull(clientProduct);
        Preconditions.checkNotNull(product);

        log.debug("Associating client product '{}' to product '{}'",
                clientProduct.getClientProductName(), product.getId());

        Product oldProduct = clientProduct.getProduct();
        maybeDeleteProduct(oldProduct);

        clientProduct.setProduct(product);
        return save(clientProduct);
    }

    private void maybeDeleteProduct(Product oldProduct) {
        List<ClientProduct> clientProducts = repository.findByProduct(oldProduct);
        if (clientProducts.isEmpty()) {
            productService.delete(oldProduct);
        } else {
            log.debug("Product '{}' has other client associated product clients. Avoiding removal", oldProduct.getId());
        }
    }

    public ClientProduct findProductByClientAndProductName(
            @NotNull @Valid Client client,
            @NotNull @NotBlank @Size(min = ClientProduct.MIN_PRODUCT_NAME_LENGTH,
                    max = ClientProduct.MAX_PRODUCT_NAME_LENGTH) String productName)
            throws ClientProductDoesNotExistException {
        Preconditions.checkNotNull(client);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(productName), "productName cannot be null or empty");

        log.debug("Creating product '{}' for client '{}'", productName, client.getClientName());
        return Optional.ofNullable(repository.findByClientAndClientProductName(client, productName))
                .orElseThrow(() -> new ClientProductDoesNotExistException(productName));
    }
}
