package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.Payment;
import com.bionic.edu.sfc.entity.builder.BillBuilder;
import com.bionic.edu.sfc.service.dao.IBillService;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IPaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;


/**
 * Ivan
 * 2014.10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class PaymentServiceImplTest {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBillService billService;

    @Test
    public void testGetAllPaymentsForCustomer() throws Exception {
        Customer customer1 = new Customer();
        customer1.setLogin("some login");
        customer1.setName("some name");

        Customer customer2 = new Customer();
        customer2.setLogin("some another login");
        customer2.setName("some another name");

        customerService.create(customer1, "password");
        customerService.create(customer2, "password");

        Bill bill1 = BillBuilder.aBill(customer1, 100).build();
        billService.create(bill1);

        Bill bill2 = BillBuilder.aBill(customer2, 100).build();
        billService.create(bill2);

        Payment payment1 = new Payment();
        payment1.setBill(bill1);
        payment1.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        payment1.setTotalSum(100d);

        Payment payment2 = new Payment();
        payment2.setBill(bill2);
        payment2.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        payment2.setTotalSum(100d);

        paymentService.create(payment1);
        paymentService.create(payment2);

        assertThat(paymentService.getAllForCustomer(customer1)).hasSize(1).contains(payment1);
        assertThat(paymentService.getAllForCustomer(customer2)).hasSize(1).contains(payment2);
    }

}
