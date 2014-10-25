package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.Payment;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IPaymentDao extends IDao<Payment> {
    public List<Payment> getAllForCustomer(Customer customer);
}
