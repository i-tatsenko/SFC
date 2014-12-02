package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.Customer;

/**
 * Ivan
 * 2014.10
 */
public interface ICustomerService extends IService<Customer> {

    void create(Customer customer, String password);

    Customer findByLogin(String login);
}
