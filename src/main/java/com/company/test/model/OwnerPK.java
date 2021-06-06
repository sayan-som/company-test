package com.company.test.model;

import lombok.*;

import java.io.Serializable;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@EqualsAndHashCode
public class OwnerPK implements Serializable {

    private String customerId;
    private String name;
    private String socialSecurityNumber;
}
