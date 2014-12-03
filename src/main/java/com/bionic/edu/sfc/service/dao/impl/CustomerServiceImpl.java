package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.ICustomerDao;
import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IUserService;
import com.bionic.edu.sfc.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Ivan
 * 2014.10
 */
@Service
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IUserService userService;

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

    @Override
    public Customer findByLogin(String login) {
        return customerDao.findByLogin(login);
    }

    @Override
    public Customer getCurrentCustomer() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null && currentUser.getUserRole() == UserRole.ROLE_CUSTOMER) {
            return (Customer) currentUser;
        }
        return null;
    }
}
