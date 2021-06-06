package com.company.test.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

@Slf4j
@Component
public class SSNValidator {

    private List<Function<String, Boolean>> validator = Arrays.asList(
            s -> s.length() == 16 ? true : false,
            s -> Pattern.matches("[a-zA-Z0-9]{16}", s));

    public Boolean validate(String socialSecurityNumber) {
        return validator.stream().allMatch(v -> v.apply(socialSecurityNumber));
    }
}
