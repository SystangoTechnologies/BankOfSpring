package com.bankofspring.service.account.exception;

import com.bankofspring.domain.model.Account;

import java.math.BigDecimal;

/**
 * Created by Arpit Khandelwal.
 */
public class InsufficientFundsException extends AccountException {
    public InsufficientFundsException(Account account, BigDecimal withdrawalAmt){
        super("Insufficient funds in account number - " + account.getAccountNumber() + ". Cannot allow withdrawal of $" + withdrawalAmt + ".");
    }
}
