package com.bankofspring.api.v1.request.account;

import com.bankofspring.domain.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccountRequest {

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long customerId;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Account.AccountType type;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long branchId;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private BigDecimal balance;

}
