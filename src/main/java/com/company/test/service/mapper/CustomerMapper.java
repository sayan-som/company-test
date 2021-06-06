package com.company.test.service.mapper;

import com.company.test.utility.CustomerUtility;
import com.company.test.model.Customer;
import com.company.test.model.Owner;
import com.company.test.resource.CustomerRequest;
import com.company.test.resource.OwnerRequest;
import com.company.test.resource.CustomerResponse;
import com.company.test.resource.OwnerResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer prepareEntity(CustomerRequest request, String newSequence) {
        return Customer.builder()
                .id(newSequence)
                .name(request.getName())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .owners(prepareEntity(request.getOwners(), newSequence))
                .build();
    }

    private Set<Owner> prepareEntity(Set<OwnerRequest> owners, String customerId) {
        return owners.stream().map(o -> {
            return Owner.builder()
                    .customerId(customerId)
                    .name(o.getName())
                    .socialSecurityNumber(o.getSocialSecurityNumber())
                    .build();
        }).collect(Collectors.toSet());
    }

    public Customer updateCustomerEntity(Customer customer, CustomerRequest request, String id) {
        customer.setName(request.getName());
        customer.setCountry(request.getCountry());
        customer.setPhoneNumber(request.getPhoneNumber());
        Set<Owner> owners = prepareEntity(request.getOwners(),id);
        customer.addOrUpdate(owners);
        return customer;
    }

    public Set<Owner> appendOwnerEntity(Set<Owner> owners, OwnerRequest request, String id) {
        owners.addAll(prepareEntity(Set.of(request),id));
        return owners;
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
