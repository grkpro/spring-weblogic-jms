package com.rk.weblogic.jms.rest;

import com.rk.weblogic.jms.dao.Customer;
import com.rk.weblogic.jms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @PostMapping("/customer/create")
    public void createCustomer(@RequestBody Customer customer) {
        customerService.create(customer);
    }
}
