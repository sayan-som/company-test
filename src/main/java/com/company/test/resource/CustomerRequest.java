package com.company.test.resource;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CustomerRequest {

    private String id;

    private String name;

    private String country;

    private String phoneNumber;

    private Set<OwnerRequest> owners;
}
