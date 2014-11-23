package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.Payment;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IPaymentService extends IService<Payment> {

    List<Payment> getAllForCustomer(Customer customer);
}
