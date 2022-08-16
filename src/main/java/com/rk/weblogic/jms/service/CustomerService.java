package com.rk.weblogic.jms.service;

import com.rk.weblogic.jms.dao.Customer;
import com.rk.weblogic.jms.publisher.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    JmsSender jmsSender;

    public void create(Customer customer) {
        jmsSender.send(customer.toString());
    }
}
