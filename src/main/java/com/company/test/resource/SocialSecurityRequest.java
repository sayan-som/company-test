package com.company.test.resource;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SocialSecurityRequest {

    private String id;

    private String socialSecurityNumber;
}
