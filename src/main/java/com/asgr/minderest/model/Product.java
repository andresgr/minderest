package com.asgr.minderest.model;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Product")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ClientProduct> clientProducts = ImmutableSet.of();
}
