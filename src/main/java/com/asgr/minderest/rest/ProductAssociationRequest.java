package com.asgr.minderest.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductAssociationRequest {

    private ProductInfo source;
    private ProductInfo target;
    
}
