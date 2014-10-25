package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.builder.UserBuilder;
import com.bionic.edu.sfc.service.dao.IUserService;
import com.bionic.edu.sfc.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


/**
 * Ivan
 * 2014.10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testCreateUser() throws Exception {
        User user = UserBuilder.anUser("some_user").build();
        String password = "some_password";
        userService.create(user, password);

        assertThat(user.getPasswordHash()).isNotNull();

        assertThat(Util.checkCredentials(password, user.getPasswordHash())).isTrue();
        assertThat(Util.checkCredentials(password + "1", user.getPasswordHash())).isFalse();
    }
}
