package com.company.test.web;

import com.company.test.resource.*;
import com.company.test.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerResponse response = service.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<List<CustomerResponse>> getCustomers() {
        List<CustomerResponse> responses = service.getCustomers();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCustomer(@PathVariable("id") String id) {
        CustomerResponse response = service.getCustomer(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<CustomerResponse> updateOwner(@PathVariable("id") String id, @RequestBody CreateOwnerRequest request) {
        CustomerResponse response = service.addUpdate(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    ResponseEntity<SocialSecurityResponse> validateSSN(@RequestBody SocialSecurityRequest request) {

        return ResponseEntity.ok(SocialSecurityResponse.builder()
                .valid(service.validateSSN(request.getSocialSecurityNumber()))
        .build());
    }
}
