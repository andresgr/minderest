package com.asgr.minderest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Entity's ID", readOnly = true)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ApiModelProperty(value = "Entity's Version", readOnly = true)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @Version
    @Column(nullable = false)
    private Long version;

}
