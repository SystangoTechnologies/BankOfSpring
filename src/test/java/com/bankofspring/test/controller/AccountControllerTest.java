package com.bankofspring.test.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.bankofspring.BankOfSpringApplication;

/**
 * Created by Arpit Khandelwal.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BankOfSpringApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class AccountControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Get all accounts test.
     * All the accounts in the database should be returned as a result of this test and validation of total 10 accounts should pass.
     *
     * @throws Exception
     */
    @Test
    public void ut1_GetAccounts() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/account/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andDo(print());
    }

    /**
     * Get Account details test.
     * Fetching the details for account with id 1 should be allowed and valid details should be returned.
     *
     * @throws Exception
     */
    @Test
    public void ut2_GetAccountByNumber() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/account/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").exists())
                .andExpect(jsonPath("$.branchId").exists())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.accountOwner.customerId").exists())
                .andExpect(jsonPath("$.accountOwner.name").exists())
                .andExpect(jsonPath("$.accountOwner.ssn").exists())
                .andExpect(jsonPath("$.accountOwner.address1").exists())
                .andExpect(jsonPath("$.accountOwner.address2").exists())
                .andExpect(jsonPath("$.accountOwner.city").exists())
                .andExpect(jsonPath("$.accountOwner.contactNumber").exists())
                .andExpect(jsonPath("$.accountNumber").value(1))
                .andExpect(jsonPath("$.branchId").value(1))
                .andExpect(jsonPath("$.type").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(100))
                .andExpect(jsonPath("$.accountOwner.customerId").value(1))
                .andExpect(jsonPath("$.accountOwner.name").value("Arpit K"))
                .andExpect(jsonPath("$.accountOwner.ssn").value("AK01"))
                .andExpect(jsonPath("$.accountOwner.address1").value("VT1"))
                .andExpect(jsonPath("$.accountOwner.address2").value("Marine Bay1"))
                .andExpect(jsonPath("$.accountOwner.city").value("Indore"))
                .andExpect(jsonPath("$.accountOwner.contactNumber").value("9425094250"))
                .andDo(print());
    }

    /**
     * Get Account by number test for invalid account number.
     * Trying to get an account's detail with invalid account number should result in a Http 400 error.
     *
     * @throws Exception
     */
    @Test
    public void ut3_GetAccountByNumber_InvalidAccount() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/account/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Account was not found for parameters {accountNumber=100}"))
                .andDo(print());
    }

    /**
     * Create Account test.
     * Account with details given in the test is created as a result of the test execution.
     *
     * @throws Exception
     */
    @Test
    public void ut4_CreateAccount() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"1\", \"branchId\":\"1\", \"type\":\"CURRENT\",\"balance\":\"1000\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").exists())
                .andExpect(jsonPath("$.branchId").exists())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.accountOwner.customerId").exists())
                .andExpect(jsonPath("$.accountOwner.name").exists())
                .andExpect(jsonPath("$.accountOwner.ssn").exists())
                .andExpect(jsonPath("$.accountOwner.address1").exists())
                .andExpect(jsonPath("$.accountOwner.address2").exists())
                .andExpect(jsonPath("$.accountOwner.city").exists())
                .andExpect(jsonPath("$.accountOwner.contactNumber").exists())
                .andExpect(jsonPath("$.accountNumber").value(11))
                .andExpect(jsonPath("$.branchId").value(1))
                .andExpect(jsonPath("$.type").value("CURRENT"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andExpect(jsonPath("$.accountOwner.customerId").value(1))
                .andExpect(jsonPath("$.accountOwner.name").value("Arpit K"))
                .andExpect(jsonPath("$.accountOwner.ssn").value("AK01"))
                .andExpect(jsonPath("$.accountOwner.address1").value("VT1"))
                .andExpect(jsonPath("$.accountOwner.address2").value("Marine Bay1"))
                .andExpect(jsonPath("$.accountOwner.city").value("Indore"))
                .andExpect(jsonPath("$.accountOwner.contactNumber").value("9425094250"))
                .andDo(print());
    }

    /**
     * Create Account test for Invalid Customer.
     * Trying to create an account for a customer with id 100 should be disallowed.
     * A Http 400 should be thrown with message indicating that customer id 100 doesn't yet exist.
     *
     * @throws Exception
     */
    @Test
    public void ut4_CreateAccount_InvalidCustomer() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"100\", \"branchId\":\"1\", \"type\":\"CURRENT\",\"balance\":\"1000\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Customer was not found for parameters {customerId=100}"))
                .andDo(print());
    }

    /**
     * Deposit Money test.
     * Account # 1 : Initial amount is $100
     * A deposit of $50 should be allowed and resultant balance should be $150.
     *
     * @throws Exception
     */
    @Test
    public void ut5_DepositMoney() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"1\", \"depositAmt\":\"50\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(1))
                .andExpect(jsonPath("$.balance").value(150))
                .andDo(print());
    }

    /**
     * Withdraw Money test.
     * Account # 1 : Amount after ut5 is $150
     * A withdrawal of 50 should be allowed and new account balance should be $100.
     *
     * @throws Exception
     */
    @Test
    public void ut6_WithdrawMoney() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"1\", \"withdrawlAmt\":\"50\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(1))
                .andExpect(jsonPath("$.balance").value(100))
                .andDo(print());
    }

    /**
     * Withdraw Money test for negative scenario.
     * Account # 1 : Amount after ut6 is $100
     * A withdrawal of $200 should raise a InsufficientFund Exception and send a Http 400 response.
     *
     * @throws Exception
     */
    @Test
    public void ut7_WithdrawMoney_InsufficientFunds() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"1\", \"withdrawlAmt\":\"200\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.apierror.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.apierror.message").value("Insufficient funds in account number - 1. Cannot allow withdrawal of $200."))
                .andDo(print());
    }

    /**
     * Transfer Money test.
     * Account # 1 : Initial Amount 100
     * Account # 2 : Initial Amount 200
     * Once the test completes, $50 should be transferred from Account # 1 to Account # 2
     *
     * @throws Exception
     */
    @Test
    public void ut8_TransferMoney() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"debitAccountNumber\":\"1\", \"creditAccountNumber\":\"2\", \"amount\":\"50\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].accountNumber").value(1))
                .andExpect(jsonPath("$.[1].accountNumber").value(2))
                .andExpect(jsonPath("$.[0].balance").value(50))
                .andExpect(jsonPath("$.[1].balance").value(250))
                .andDo(print());
    }

    /**
     * Transfer Money test to a non existent recipient.
     * Account # 1 : Initial Amount 100
     * Account # 2 : doesn't exist
     * Once the test completes, an Http 404 error should be returned indicating the recipient doesn't exist.
     *
     * @throws Exception
     */
    @Test
    public void ut9_TransferMoney_InvalidRecipient() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"debitAccountNumber\":\"1\", \"creditAccountNumber\":\"20\", \"amount\":\"50\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Account was not found for parameters {accountNumber=20}"))
                .andDo(print());
    }

}
