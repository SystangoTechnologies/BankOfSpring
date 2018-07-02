package com.bankofspring.service.customer;

import com.bankofspring.domain.model.Customer;
import com.bankofspring.domain.repository.CustomerRepository;
import com.bankofspring.dto.CustomerDto;
import com.bankofspring.exception.DuplicateEntityException;
import com.bankofspring.exception.EntityException;
import com.bankofspring.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Arpit Khandelwal.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Customer> getAllCustomers() {
        Iterator<Customer> iteratorToCollection = customerRepository.findAll().iterator();
        return StreamSupport.stream(
                Spliterators
                        .spliteratorUnknownSize(iteratorToCollection, Spliterator.ORDERED), false)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Customer getCustomer(String ssn) throws EntityNotFoundException {
        Customer customer = customerRepository.findBySsn(ssn);
        if (customer != null) {
            return customer;
        }
        throw new EntityNotFoundException(Customer.class, "ssn", ssn.toString());

    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) throws EntityException {
        Optional<Customer> customerInDb = Optional.ofNullable(customerRepository.findBySsn(customerDto.getSsn()));
        if (customerInDb.isPresent()) {
            throw new DuplicateEntityException(Customer.class, "ssn", customerDto.getSsn().toString());
        }
        Customer customer = mapper.map(customerDto, Customer.class);
        return customerRepository.save(customer);
    }
}
