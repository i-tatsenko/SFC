package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.ICustomerDao;
import com.bionic.edu.sfc.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.09
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class CustomerDaoImpl extends ADao<Customer> implements ICustomerDao {

    public CustomerDaoImpl() {
        super(Customer.class);
    }

    @Override
    public Customer findByLogin(String login) {
        return (Customer) getSession().createQuery("" +
                "FROM Customer " +
                "WHERE login=:login " +
                "AND visible=true")
                .setParameter("login", login).uniqueResult();
    }
}
