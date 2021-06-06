package com.company.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@Entity
@Table(name = "CUSTOMER")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")


public class Customer implements Persistable<String> {


    public Customer() {
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "CUSTOMER_SEQUENCE", initialValue = 1001)
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<Owner> owners;

    @Override
    public boolean isNew() {
        return true;
    }
}
