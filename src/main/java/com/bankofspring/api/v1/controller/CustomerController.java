package com.bankofspring.api.v1.controller;

import com.bankofspring.api.v1.request.customer.CreateCustomerRequest;
import com.bankofspring.domain.model.Customer;
import com.bankofspring.dto.CustomerDto;
import com.bankofspring.exception.EntityException;
import com.bankofspring.exception.EntityNotFoundException;
import com.bankofspring.service.customer.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arpit Khandelwal.
 */
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers != null && !customers.isEmpty()) {
            return customers
                    .stream()
                    .map(customer -> mapper.map(customer, CustomerDto.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @GetMapping(value = "/{ssn}")
    public CustomerDto getCustomer(@PathVariable("ssn") String ssn) throws EntityNotFoundException {
        Customer customer = customerService.getCustomer(ssn);
        if (customer != null) {
            return mapper.map(customer, CustomerDto.class);
        }
        return null;
    }

    @PostMapping("/create")
    public CustomerDto createCustomer(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) throws EntityException {
        CustomerDto customerDto = mapper.map(createCustomerRequest, CustomerDto.class);
        Customer customer = customerService.createCustomer(customerDto);
        if (customer != null) {
            return mapper.map(customer, CustomerDto.class);
        }
        return null;
    }
}
