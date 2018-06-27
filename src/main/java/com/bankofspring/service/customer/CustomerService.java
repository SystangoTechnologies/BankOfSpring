package com.bankofspring.service.customer;

import com.bankofspring.domain.model.Customer;
import com.bankofspring.dto.CustomerDto;
import com.bankofspring.exception.EntityException;
import com.bankofspring.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto) throws EntityException;

    Customer getCustomer(String ssn) throws EntityNotFoundException;

    List<Customer> getAllCustomers();
}
