package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IPaymentDao;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.Payment;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class PaymentDaoImpl extends ADao<Payment> implements IPaymentDao {


    public PaymentDaoImpl() {
        super(Payment.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Payment> getAllForCustomer(Customer customer) {
        return (List<Payment>)getSession().createQuery("FROM Payment WHERE bill.customer=:customer")
                .setParameter("customer", customer)
                .list();
    }
}
