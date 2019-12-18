package com.asgr.minderest.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Delegate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ClientProduct")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ClientProduct extends BaseEntity {

    public static final int MIN_PRODUCT_NAME_LENGTH = 1;
    public static final int MAX_PRODUCT_NAME_LENGTH = 255;

    @NotNull @NotBlank
    @Size(min = MIN_PRODUCT_NAME_LENGTH, max = MAX_PRODUCT_NAME_LENGTH)
    private String clientProductName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client")
    @Delegate
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product")
    private Product product;

    public ClientProduct(Client client, Product product, String productName) {
        this.client = Preconditions.checkNotNull(client);
        this.product = Preconditions.checkNotNull(product);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(productName), "productName cannot be null or empty");
        clientProductName = productName;
    }

}
