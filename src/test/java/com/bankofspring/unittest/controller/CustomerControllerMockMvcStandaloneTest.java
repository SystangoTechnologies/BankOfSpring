package com.bankofspring.unittest.controller;

import com.bankofspring.api.v1.controller.CustomerController;
import com.bankofspring.domain.model.Customer;
import com.bankofspring.dto.CustomerDto;
import com.bankofspring.exception.EntityNotFoundException;
import com.bankofspring.service.customer.CustomerService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Arpit Khandelwal.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerMockMvcStandaloneTest {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Before
    public void setup(){}

    /**
     * Get all customers unit-test.
     * The unit test should result in returning the 2 customers which are provided by us as input.
     *
     * @throws Exception
     */
    @Test
    public void ut1_GetAllCustomers() throws Exception{
        List<Customer> customers = new ArrayList<>(2);
        customers.add(new Customer().setSsn("ssn1").setName("Cust1").setCity("London"));
        customers.add(new Customer().setSsn("ssn2").setName("Cust2").setCity("London"));

        // given
        doReturn(customers).when(customerService).getAllCustomers();

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/customer/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    /**
     * Get customer by ssn unit-test.
     * The test should result in returning customer's details for SSN:AK01 as provided by us as input.
     *
     * @throws Exception
     */
    @Test
    public void ut2_GetCustomerBySsn() throws Exception{
        Customer customer = new Customer()
                .setCustomerId(1L)
                .setName("Arpit K")
                .setSsn("AK01")
                .setAddress1("VT1")
                .setAddress2("Marine Bay1")
                .setCity("SpringCity")
                .setContactNumber("9425094250");

        // given
        doReturn(customer).when(customerService).getCustomer("AK01");

        //when, then
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
                .andExpect(jsonPath("$.city").value("SpringCity"))
                .andExpect(jsonPath("$.contactNumber").value("9425094250"))
                .andDo(print());
    }

    /**
     * Get customer by invalid ssn unit-test.
     * The test should result in returning a Http 404 for a customer with invalid ssn.
     *
     * @throws Exception
     */
    @Test
    public void ut3_GetCustomerBySsn_InvalidSsn() throws Exception {
        // given
        doThrow(new EntityNotFoundException(Customer.class, "ssn", "AK02")).when(customerService).getCustomer("AK02");

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/customer/AK02").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.apierror.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.apierror.message").value("Customer was not found for parameters {ssn=AK02}"))
                .andDo(print());
    }

    /**
     * Create customer unit-test.
     * The test should result in adding a new customer with SSN:TK01
     *
     * @throws Exception
     */
    @Test
    public void ut4_CreateCustomer() throws Exception{
        Customer customer = new Customer()
                .setCustomerId(1L)
                .setName("Test Customer")
                .setSsn("TK01")
                .setAddress1("Unit-Test")
                .setAddress2("Spring-Boot")
                .setCity("SpringCity")
                .setContactNumber("9425094250");

        // given
        doReturn(customer).when(customerService).createCustomer(mapper.map(customer, CustomerDto.class));

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Customer\", \"ssn\":\"TK01\", \"contactNumber\":\"9425094250\",\"address1\":\"Unit-Test\",\"address2\":\"Spring-Boot\",\"city\":\"SpringCity\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.ssn").exists())
                .andExpect(jsonPath("$.address1").exists())
                .andExpect(jsonPath("$.address2").exists())
                .andExpect(jsonPath("$.city").exists())
                .andExpect(jsonPath("$.contactNumber").exists())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.ssn").value("TK01"))
                .andExpect(jsonPath("$.address1").value("Unit-Test"))
                .andExpect(jsonPath("$.address2").value("Spring-Boot"))
                .andExpect(jsonPath("$.city").value("SpringCity"))
                .andExpect(jsonPath("$.contactNumber").value("9425094250"))
                .andDo(print());
    }


}
