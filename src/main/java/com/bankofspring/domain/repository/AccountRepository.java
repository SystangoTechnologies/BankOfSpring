package com.bankofspring.domain.repository;

import com.bankofspring.domain.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arpit Khandelwal.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
