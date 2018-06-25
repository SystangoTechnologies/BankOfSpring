package com.bankofspring.service.account.exception;

import com.bankofspring.exception.BankException;

/**
 * Created by Arpit Khandelwal.
 */
public class AccountException extends BankException {
    public AccountException(String message){
        super(message);
    }
}
