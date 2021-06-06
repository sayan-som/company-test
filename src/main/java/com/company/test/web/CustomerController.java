package com.company.test.web;

import com.company.test.resource.*;
import com.company.test.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.test.utility.CustomerUtility.cleanUp;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = service.createCustomer(request);
        cleanUp();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        List<CustomerResponse> responses = service.getCustomers();
        cleanUp();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") String id) {
        CustomerResponse response = service.getCustomer(id);
        cleanUp();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateOwner(@PathVariable("id") String id, @RequestBody OwnerRequest request) {
        CustomerResponse response = service.addUpdate(id, request);
        cleanUp();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<SocialSecurityResponse> createCustomer(@RequestBody SocialSecurityRequest request) {
        SocialSecurityResponse response = SocialSecurityResponse.builder()
                .valid(service.validateSSN(request.getSocialSecurityNumber()))
                .build();
        cleanUp();
        return ResponseEntity.ok(response);
    }
}
