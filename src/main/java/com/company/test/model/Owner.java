package com.company.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Builder
@Data
@Table(name = "OWNER")
@AllArgsConstructor
@EqualsAndHashCode(of = {"customerId", "name", "socialSecurityNumber"})
@IdClass(OwnerPK.class)
public class Owner implements Persistable<OwnerPK>, Comparable<Owner>{

    public Owner() {
    }

    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Id
    @Column(name = "OWNER_NAME")
    private String name;

    @Id
    @Column(name = "OWNER_SSN")
    private String socialSecurityNumber;

    @Override
    public OwnerPK getId() {
        return OwnerPK.builder()
                .customerId(customerId)
                .name(name)
                .socialSecurityNumber(socialSecurityNumber)
                .build();
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public int compareTo(@NotNull Owner owner) {
        return Comparator.comparing(Owner::getCustomerId)
                .thenComparing(Owner::getName)
                .thenComparing(Owner::getSocialSecurityNumber)
                .compare(this, owner);
    }
}
