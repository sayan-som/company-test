package com.company.test.service.mapper;

import com.company.test.model.Customer;
import com.company.test.model.Owner;
import com.company.test.resource.CustomerRequest;
import com.company.test.resource.CustomerResponse;
import com.company.test.resource.OwnerRequest;
import com.company.test.resource.OwnerResponse;
import com.company.test.utility.CustomerUtility;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer createCustomer(CustomerRequest request, String newSequence) {
        return Customer.builder()
                .id(newSequence)
                .name(request.getName())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .owners(createOwners(request.getOwners(), newSequence))
                .version(0L)
                .build();
    }

    private Set<Owner> createOwners(Set<OwnerRequest> owners, String customerId) {
        return owners.stream().map(o -> {
            return Owner.builder()
                    .customerId(customerId)
                    .name(o.getName())
                    .socialSecurityNumber(o.getSocialSecurityNumber())
                    .build();
        }).collect(Collectors.toSet());
    }

    public Customer updateCustomer(CustomerRequest request, Customer customer, String id) {
        BeanUtils.copyProperties(request, customer, "owners");
        customer.setId(id);
        customer.getOwners().clear();
        customer.getOwners().addAll(updateOwners(request.getOwners(), id));
        customer.incrementVersion();
        return customer;
    }

    public Set<Owner> updateOwners(Set<OwnerRequest> requests, String id) {
        return requests.stream().map(o -> {
            return Owner.builder()
                    .customerId(id)
                    .name(o.getName())
                    .socialSecurityNumber(o.getSocialSecurityNumber())
                    .build();
        }).collect(Collectors.toSet());
    }

    public CustomerResponse prepareResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .country(customer.getCountry())
                .phoneNumber(customer.getPhoneNumber())
                .owners(prepareResponse(customer.getOwners()))
                .build();
    }

    private Set<OwnerResponse> prepareResponse(Set<Owner> owners) {
        Boolean flag = CustomerUtility.hasWriteRights();
        return owners.stream().map(o -> {
            return OwnerResponse.builder()
                    .name(o.getName())
                    .socialSecurityNumber(flag ? o.getSocialSecurityNumber() : null)
                    .build();
        }).collect(Collectors.toSet());
    }

    public List<CustomerResponse> prepareResponse(Iterator<Customer> customers) {
        List<CustomerResponse> responses = new ArrayList<>();
        while (customers.hasNext()) {
            responses.add(prepareResponse(customers.next()));
        }
        return responses;
    }
}
