package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by docent on 03.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class CustomerServiceImplTest {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserService userService;

    @Test
    public void getCustomerTest() throws Exception {
        Customer customer = new Customer();
        customer.setLogin("login");
        customer.setName("name");

        customerService.create(customer, "pass");

        customer.setPrepaymentRate(0.5f);
        customerService.update(customer);

        Customer customerFromDb = (Customer) userService.findUserByLogin(customer.getLogin());
        assertThat(customerFromDb).isNotNull();
        assertThat(customerFromDb.getPrepaymentRate()).isEqualTo(0.5f);
    }

    @Test(expected = ClassCastException.class)
    public void getCustomerFailTest() {
        User user = new User();
        user.setUserRole(UserRole.ROLE_ACCOUNTANT);
        user.setLogin("login1");
        user.setName("name");
        user.setActive(true);
        userService.create(user, "pass");

        Customer customer = (Customer) userService.findUserByLogin(user.getLogin());
    }
}
