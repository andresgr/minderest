package com.asgr.minderest.rest;

import com.asgr.minderest.model.Client;
import com.asgr.minderest.model.ClientProduct;
import com.asgr.minderest.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/product")
public class ProductController implements Serializable {

    private final long serialVersionUID = 0L;

    @Autowired
    private ClientProductService clientProductService;

    @Autowired
    private ClientService clientService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInfo createProduct(@RequestBody @NotNull @Valid ProductInfo request)
            throws ClientDoesNotExistException, ClientProductAlreadyExists {
        String clientId = request.getClientId();
        String productName = request.getProductName();
        log.debug("Creating product with name '{}' for client '{}'", clientId, productName);
        ClientProduct clientProduct = clientProductService.createProduct(clientService.findByClientId(clientId), productName);
        return ProductInfo.of(clientProduct);
    }

    @GetMapping
    @ResponseBody
    public List<ProductInfo> findOtherClientProducts(@RequestBody @NotNull @Valid ProductInfo request)
            throws ClientDoesNotExistException, ClientProductDoesNotExistException {
        String clientId = request.getClientId();
        String productName = request.getProductName();
        log.debug("Finding corresponding client '{}' products for product '{}'", clientId, productName);
        Client client = clientService.findByClientId(clientId);
        List<ClientProduct> clientProducts = clientProductService.findOtherClientProducts(
                clientProductService.findProductByClientAndProductName(client, productName));
        return clientProducts.stream()
                .map(ProductInfo::of)
                .collect(Collectors.toList());
    }

    @PutMapping
    @ResponseBody
    public void associate(@RequestBody @NotNull @Valid ProductAssociationRequest request)
            throws ClientDoesNotExistException, ClientProductDoesNotExistException {
        ProductInfo source = request.getSource();
        ProductInfo target = request.getTarget();
        log.debug("Associating product '{}' to product '{}'", source, target);

        Client sourceClient = clientService.findByClientId(source.getClientId());
        ClientProduct sourceClientProduct =
                clientProductService.findProductByClientAndProductName(sourceClient, source.getProductName());

        Client targetClient = clientService.findByClientId(source.getClientId());
        ClientProduct targetClientProduct =
                clientProductService.findProductByClientAndProductName(targetClient, target.getProductName());
        clientProductService.setClientProduct(targetClientProduct, sourceClientProduct.getProduct());
    }

}
