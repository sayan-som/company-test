package com.company.test.service;

import com.company.test.exception.CustomerNotFoundException;
import com.company.test.model.Customer;
import com.company.test.model.Owner;
import com.company.test.repository.CustomerRepository;
import com.company.test.resource.CustomerRequest;
import com.company.test.resource.CustomerResponse;
import com.company.test.resource.OwnerRequest;
import com.company.test.service.mapper.CustomerMapper;
import com.company.test.validator.SSNValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private SSNValidator validator;

    public CustomerResponse createCustomer(CustomerRequest request) {
        String newSequence = sequenceGenerator.getSequence();

        Customer customer = mapper.createCustomer(request, newSequence);
        log.debug("Creating Customer : {} ", customer);

        repository.save(customer);

        return mapper.prepareResponse(customer);
    }

    public List<CustomerResponse> getCustomers() {
        log.debug("Finding all Customers ....");

        Iterable<Customer> customers = repository.findAll();
        return mapper.prepareResponse(customers.iterator());
    }

    public CustomerResponse getCustomer(String id) {
        log.debug("Finding Customers by Id : {}", id);

        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
        return mapper.prepareResponse(customer);
    }

    public CustomerResponse updateCustomer(String id, CustomerRequest request) {

        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
        mapper.updateCustomer(request, customer, id);
        repository.save(customer);

        return mapper.prepareResponse(customer);
    }

    public CustomerResponse updateOwner(Set<OwnerRequest> requests, String id) {

        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
        Set<Owner> owners = mapper.updateOwners(requests, id);
        customer.getOwners().clear();
        customer.getOwners().addAll(owners);

        repository.save(customer);

        return mapper.prepareResponse(customer);
    }

    public void deleteCustomer(String id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
        repository.delete(customer);
    }

    public Boolean validateSSN(String socialSecurityNumber) {
        return validator.validate(socialSecurityNumber);
    }
}
