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

    @Test
    public void ut1_GetAccounts() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/account/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andDo(print());
    }

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

    @Test
    public void ut3_GetAccountByNumber_InvalidAccount() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/account/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Account was not found for parameters {accountNumber=100}"))
                .andDo(print());
    }

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
}
