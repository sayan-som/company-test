package com.company.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Builder
@Data
@Table(name = "OWNER")
@AllArgsConstructor
@EqualsAndHashCode(of = {"customerId", "name", "socialSecurityNumber"})
@IdClass(OwnerPK.class)
public class Owner implements Persistable<OwnerPK> {

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
}
