package com.bankofspring.domain.repository;

import com.bankofspring.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arpit Khandelwal.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findBySsn(String ssn);
}
