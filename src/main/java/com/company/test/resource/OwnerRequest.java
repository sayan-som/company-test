package com.company.test.resource;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OwnerRequest {

    private String name;

    private String socialSecurityNumber;
}
