package com.bankofspring.api.v1.controller;

import com.bankofspring.api.v1.request.account.CreateAccountRequest;
import com.bankofspring.api.v1.request.account.DepositRequest;
import com.bankofspring.api.v1.request.account.WithdrawalRequest;
import com.bankofspring.domain.model.Account;
import com.bankofspring.dto.AccountDto;
import com.bankofspring.exception.EntityException;
import com.bankofspring.exception.EntityNotFoundException;
import com.bankofspring.service.account.AccountService;
import com.bankofspring.service.account.exception.InsufficientFundsException;
import com.bankofspring.api.v1.request.account.TransferFundRequest;
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
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/")
    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts != null && !accounts.isEmpty()) {
            List<AccountDto> accountDtos = accounts
                    .stream()
                    .map(account -> modelMapper.map(account, AccountDto.class))
                    .collect(Collectors.toList());
            return accountDtos;
        }
        return Collections.emptyList();
    }

    @GetMapping(value = "/{accountNumber}")
    public AccountDto getAccountByNumber(@PathVariable("accountNumber") Long accountNumber) throws EntityNotFoundException {
        Account account = accountService.getAccount(accountNumber);
        if (account != null) {
            AccountDto accountDto = modelMapper.map(account, AccountDto.class);
            return accountDto;
        }
        return null;
    }

    @PostMapping("/create")
    public AccountDto createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) throws EntityException {
        AccountDto accountDto = modelMapper.map(createAccountRequest, AccountDto.class);
        Account account = accountService.createAccount(accountDto);
        if (account != null) {
            AccountDto resultAccount = modelMapper.map(account, AccountDto.class);
            return resultAccount;
        }
        return null;
    }

    @PostMapping("/deposit")
    public AccountDto depositMoney(@RequestBody @Valid DepositRequest depositRequest) throws EntityNotFoundException {
        AccountDto accountDto = modelMapper.map(depositRequest, AccountDto.class);
        Account account = accountService.creditAmount(accountDto, depositRequest.getDepositAmt());
        if (account != null) {
            AccountDto resultAccount = modelMapper.map(account, AccountDto.class);
            return resultAccount;
        }
        return null;
    }

    @PostMapping("/withdraw")
    public AccountDto withdrawMoney(@RequestBody @Valid WithdrawalRequest withdrawalRequest) throws EntityNotFoundException, InsufficientFundsException {
        AccountDto accountDto = modelMapper.map(withdrawalRequest, AccountDto.class);
        Account account = accountService.debitAmount(accountDto, withdrawalRequest.getWithdrawlAmt());
        if (account != null) {
            AccountDto resultAccount = modelMapper.map(account, AccountDto.class);
            return resultAccount;
        }
        return null;
    }

    @PostMapping("/transfer")
    public List<AccountDto> transferMoney(@RequestBody @Valid TransferFundRequest transferFundRequest) throws InsufficientFundsException, EntityNotFoundException {
        AccountDto debitAccountDto = new AccountDto().setAccountNumber(transferFundRequest.getDebitAccountNumber());
        AccountDto creditAccountDto = new AccountDto().setAccountNumber(transferFundRequest.getCreditAccountNumber());
        List<Account> accounts = accountService.transferFunds(debitAccountDto,creditAccountDto,transferFundRequest.getAmount());
        return accounts
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }
}
