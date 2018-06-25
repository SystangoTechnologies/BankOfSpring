package com.bankofspring.domain.repository;

import com.bankofspring.domain.model.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arpit Khandelwal.
 */
@Repository
public interface BranchRepository extends CrudRepository<Branch, Long> {
}
