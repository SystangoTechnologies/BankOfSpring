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
public class CustomerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Get all customers test.
     * The test should result in returning the 5 customers which are present in the test database.
     *
     * @throws Exception
     */
    @Test
    public void ut1_GetAllCustomers() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/customer/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andDo(print());
    }

    /**
     * Get customer by ssn test.
     * The test should result in returning customer's details for SSN:AK01
     *
     * @throws Exception
     */
    @Test
    public void ut2_GetCustomerBySsn() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/customer/AK01").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.ssn").exists())
                .andExpect(jsonPath("$.address1").exists())
                .andExpect(jsonPath("$.address2").exists())
                .andExpect(jsonPath("$.city").exists())
                .andExpect(jsonPath("$.contactNumber").exists())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("Arpit K"))
                .andExpect(jsonPath("$.ssn").value("AK01"))
                .andExpect(jsonPath("$.address1").value("VT1"))
                .andExpect(jsonPath("$.address2").value("Marine Bay1"))
                .andExpect(jsonPath("$.city").value("Indore"))
                .andExpect(jsonPath("$.contactNumber").value("9425094250"))
                .andDo(print());
    }

    /**
     * Get customer by invalid ssn test.
     * The test should result in returning a Http 404 for a customer with invalid ssn.
     *
     * @throws Exception
     */
    @Test
    public void ut3_GetCustomerBySsn_InvalidSsn() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/customer/AK02").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Customer was not found for parameters {ssn=AK02}"))
                .andDo(print());
    }

    /**
     * Create customer test.
     * The test should result in adding a new customer to the database with SSN:TK01
     *
     * @throws Exception
     */
    @Test
    public void ut4_CreateCustomer() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Customer\", \"ssn\":\"TK01\", \"contactNumber\":\"9425094255\",\"address1\":\"Unit-Test\",\"address2\":\"Spring-Boot\",\"city\":\"SpringCity\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.ssn").exists())
                .andExpect(jsonPath("$.address1").exists())
                .andExpect(jsonPath("$.address2").exists())
                .andExpect(jsonPath("$.city").exists())
                .andExpect(jsonPath("$.contactNumber").exists())
                .andExpect(jsonPath("$.customerId").value(6))
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.ssn").value("TK01"))
                .andExpect(jsonPath("$.address1").value("Unit-Test"))
                .andExpect(jsonPath("$.address2").value("Spring-Boot"))
                .andExpect(jsonPath("$.city").value("SpringCity"))
                .andExpect(jsonPath("$.contactNumber").value("9425094255"))
                .andDo(print());
    }

    /**
     * Create duplicate customer test.
     * Since a customer with SSN:TK01 has already been created in ut4, this test should result in Http 404.
     *
     * @throws Exception
     */
    @Test
    public void ut5_CreateCustomer_Duplicate() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Customer\", \"ssn\":\"TK01\", \"contactNumber\":\"9425094255\",\"address1\":\"Unit-Test\",\"address2\":\"Spring-Boot\",\"city\":\"SpringCity\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.apierror.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.apierror.message").value("Customer was already found for parameters {ssn=TK01}"))
                .andDo(print());
    }
}
