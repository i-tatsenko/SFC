package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.Customer;


/**
 * Ivan
 * 2014.09
 */
public interface ICustomerDao extends IDao<Customer> {

    Customer findByLogin(String login);
}
