package com.company.test.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialSecurityResponse {

    private Boolean valid;
}
