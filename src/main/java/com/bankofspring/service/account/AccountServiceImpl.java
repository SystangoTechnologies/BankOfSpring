package com.bankofspring.service.account;

import com.bankofspring.domain.model.Account;
import com.bankofspring.domain.model.Branch;
import com.bankofspring.domain.model.Customer;
import com.bankofspring.domain.repository.CustomerRepository;
import com.bankofspring.exception.EntityException;
import com.bankofspring.exception.EntityNotFoundException;
import com.bankofspring.service.account.exception.InsufficientFundsException;
import com.bankofspring.domain.repository.AccountRepository;
import com.bankofspring.domain.repository.BranchRepository;
import com.bankofspring.dto.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Arpit Khandelwal.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<Account> getAllAccounts() {
        Iterator<Account> iteratorToCollection = accountRepository.findAll().iterator();
        return StreamSupport.stream(
                Spliterators
                        .spliteratorUnknownSize(iteratorToCollection, Spliterator.ORDERED), false)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Account getAccount(Long accountNumber) throws EntityNotFoundException {
        Optional<Account> accountInDb = accountRepository.findById(accountNumber);
        if (accountInDb.isPresent()) {
            return accountInDb.get();
        }
        throw new EntityNotFoundException(Account.class, "accountNumber", accountNumber.toString());
    }

    @Override
    @Transactional
    public Account createAccount(AccountDto accountDto) throws EntityException {
        Optional<Customer> customer = customerRepository.findById(accountDto.getCustomerId());
        Optional<Branch> branch = branchRepository.findById(accountDto.getBranchId());
        if (customer.isPresent()) {
            if (branch.isPresent()) {
                Account account = mapper.map(accountDto, Account.class);
                account.setCoreBranch(branch.get());
                account.setAccountOwner(customer.get());
                return accountRepository.save(account);
            }
            throw new EntityNotFoundException(Branch.class, "branchId", accountDto.getBranchId().toString());
        }
        throw new EntityNotFoundException(Customer.class, "customerId", accountDto.getCustomerId().toString());
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = EntityNotFoundException.class, isolation= Isolation.READ_COMMITTED)
    public Account creditAmount(AccountDto accountDto, BigDecimal creditAmt) throws EntityNotFoundException {
        assert(creditAmt.compareTo(BigDecimal.ZERO) == 1); //assert greater than 0
        Optional<Account> accountInDb = accountRepository.findById(accountDto.getAccountNumber());
        if (accountInDb.isPresent()) {
            Account account = accountInDb.get();
            account.credit(creditAmt);
            return accountRepository.save(account);
        }
        throw new EntityNotFoundException(Account.class, "accountNumber", accountDto.getAccountNumber().toString());
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = {InsufficientFundsException.class, EntityNotFoundException.class}, isolation=Isolation.READ_COMMITTED)
    public Account debitAmount(AccountDto accountDto, BigDecimal debitAmt) throws EntityNotFoundException, InsufficientFundsException {
        assert(debitAmt.compareTo(BigDecimal.ZERO) == 1); //assert greater than 0
        Optional<Account> accountInDb = accountRepository.findById(accountDto.getAccountNumber());
        if (accountInDb.isPresent()) {
            Account account = accountInDb.get();
            account.debit(debitAmt);
            return accountRepository.save(account);
        }
        throw new EntityNotFoundException(Account.class, "accountNumber", accountDto.getAccountNumber().toString());
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = {InsufficientFundsException.class, EntityNotFoundException.class}, isolation=Isolation.READ_COMMITTED)
    public List<Account> transferFunds(AccountDto debitAccountDto, AccountDto creditAccountDto, BigDecimal amount) throws EntityNotFoundException, InsufficientFundsException {
        assert(amount.compareTo(BigDecimal.ZERO) == 1); //assert greater than 0
        Account debitAccount = debitAmount(debitAccountDto, amount);
        Account creditAccount = creditAmount(creditAccountDto, amount);
        return Stream
                .of(debitAccount, creditAccount)
                .collect(Collectors.toList());
    }
}
