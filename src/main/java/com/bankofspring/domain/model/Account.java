package com.bankofspring.domain.model;

import com.bankofspring.service.account.exception.InsufficientFundsException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Arpit Khandelwal.
 */
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
public class Account extends BaseDomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch coreBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer accountOwner;

    private BigDecimal balance;

    private AccountType type;

    /**
     * Debits the given amount from current account balance.
     * @param debitAmount
     * @throws InsufficientFundsException
     */
    public void debit(BigDecimal debitAmount) throws InsufficientFundsException {
        if(this.balance.compareTo(debitAmount) >= 0){
            this.balance = this.balance.subtract(debitAmount);
            return;
        }
        throw new InsufficientFundsException(this,debitAmount);
    }

    /**
     * Credits the given amount to current account balance
     * @param creditAmount
     */
    public void credit(BigDecimal creditAmount) {
        this.balance = this.balance.add(creditAmount);
    }

    public enum AccountType {
        SAVINGS, CURRENT, LOAN;
    }
}
