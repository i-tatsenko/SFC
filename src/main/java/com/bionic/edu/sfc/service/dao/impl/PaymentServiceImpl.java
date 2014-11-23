package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IPaymentDao;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.service.dao.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentDao paymentDao;

    @Override
    public final IDao<Payment> getDao() {
        return paymentDao;
    }

    @Override
    public List<Payment> getAllForCustomer(Customer customer) {
        return paymentDao.getAllForCustomer(customer);
    }
}
