package com.asgr.minderest.rest;

import com.asgr.minderest.model.ClientProduct;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.asgr.minderest.model.Client.MAX_CLIENT_ID_LENGTH;
import static com.asgr.minderest.model.Client.MIN_CLIENT_ID_LENGTH;
import static com.asgr.minderest.model.ClientProduct.MAX_PRODUCT_NAME_LENGTH;
import static com.asgr.minderest.model.ClientProduct.MIN_PRODUCT_NAME_LENGTH;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    @NotNull @NotBlank
    @Size(min = MIN_CLIENT_ID_LENGTH, max = MAX_CLIENT_ID_LENGTH)
    private String clientId;

    @NotNull
    @NotBlank
    @Size(min = MIN_PRODUCT_NAME_LENGTH, max = MAX_PRODUCT_NAME_LENGTH)
    private String productName;

    public static ProductInfo of(@NotNull @Valid ClientProduct clientProduct) {
        Preconditions.checkNotNull(clientProduct);

        return new ProductInfo(clientProduct.getClientId(), clientProduct.getClientProductName());
    }
}
