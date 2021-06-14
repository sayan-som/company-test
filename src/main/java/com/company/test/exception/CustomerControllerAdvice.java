package com.company.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.company.test.utility.CustomerUtility.cleanUp;

@ControllerAdvice
public class CustomerControllerAdvice {

    private static final Map<String, String> ERROR_MSG_MAP = new HashMap<>() {{
        put("message", "Something went wrong");
    }};

    private static final Map<String, Map> ERROR_MAP = new HashMap<>() {{
        put("error", ERROR_MSG_MAP);
    }};

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity getCustomerNotFoundExceptionResponse() {
        cleanUp();
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity getExceptionResponse() {
        cleanUp();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_MAP);
    }
}
