package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.ICustomerDao;
import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public final IDao<Customer> getDao() {
        return customerDao;
    }

    @Override
    public void create(Customer object) {
        throw new IllegalAccessError();
    }

    @Override
    public void create(Customer customer, String password) {
        customer.setCreationDate(new Date());
        customer.setPasswordHash(Util.getPasswordHash(password));
        customer.setActive(true);
        customer.setUserRole(UserRole.ROLE_CUSTOMER);
        customer.setPrepaymentRate(1);
        customerDao.create(customer);

    }
}
