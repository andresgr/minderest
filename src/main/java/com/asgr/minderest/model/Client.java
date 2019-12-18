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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "Client")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"clientProducts"})
public class Client extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int MIN_CLIENT_ID_LENGTH = 4;
    public static final int MAX_CLIENT_ID_LENGTH = 36;
    public static final int MAX_CLIENT_NAME_LENGTH = 200;
    public static final int MIN_CLIENT_NAME_LENGTH = 1;

    @NotNull @NotBlank
    @Size(min = MIN_CLIENT_ID_LENGTH, max = MAX_CLIENT_ID_LENGTH)
    private String clientId;

    @NotNull @NotBlank
    @Size(min = MIN_CLIENT_NAME_LENGTH, max = MAX_CLIENT_NAME_LENGTH)
    private String clientName;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<ClientProduct> clientProducts = ImmutableSet.of();

}
