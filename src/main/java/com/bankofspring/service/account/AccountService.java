package com.bankofspring.service.account;

import com.bankofspring.domain.model.Account;
import com.bankofspring.exception.EntityNotFoundException;
import com.bankofspring.service.account.exception.InsufficientFundsException;
import com.bankofspring.dto.AccountDto;
import com.bankofspring.exception.EntityException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
public interface AccountService {
    Account createAccount(AccountDto accountDto) throws EntityException;

    List<Account> getAllAccounts();

    Account getAccount(Long accountNumber) throws EntityNotFoundException;

    Account creditAmount(AccountDto accountDto, BigDecimal depositAmt) throws EntityNotFoundException;

    Account debitAmount(AccountDto accountDto, BigDecimal withdrawalAmt) throws EntityNotFoundException, InsufficientFundsException;

    List<Account> transferFunds(AccountDto fromAccountDto, AccountDto toAccountDto, BigDecimal amount) throws EntityNotFoundException, InsufficientFundsException;

}
