package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IUserDao;
import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;


/**
 * Ivan
 * 2014.09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:sfcBootstrapTest.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class UserDaoImplTest {

    @Autowired
    private IUserDao userDao;

    private Random random = new Random();

    @Test
    public void testCreate() throws Exception {
        User user = getUser();
        userDao.create(user);

        assertThat(user.getId()).isNotEqualTo(0);

        User userFromDb = userDao.findById(user.getId());
        assertThat(userFromDb).isEqualTo(user);
    }

    @Test
    public void testFindByLogin() throws Exception {
        User user = getUser();
        userDao.create(user);

        assertThat(userDao.findByLogin("some strange login")).isEqualTo(null);

        assertThat(userDao.findByLogin(user.getLogin())).isEqualTo(user);
    }

    @Test
    public void testGetAll() throws Exception {
        User user1 = getUser();
        User user2 = getUser();

        userDao.create(user1);
        userDao.create(user2);

        assertThat(userDao.getAll()).contains(user1, user2);

    }

    private User getUser() {
        return UserBuilder.anUser(String.valueOf(random.nextInt()))
                .withPasswordHash("some hash")
                .withActive(true)
                .build();
    }
}
