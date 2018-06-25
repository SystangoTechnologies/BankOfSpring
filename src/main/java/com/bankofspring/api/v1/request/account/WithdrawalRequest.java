package com.bankofspring.api.v1.request.account;

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
public class WithdrawalRequest {
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long accountNumber;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private BigDecimal withdrawlAmt;
}
