package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IUserDao;
import com.bionic.edu.sfc.entity.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Ivan
 * 2014.09
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
public class UserDaoImpl extends ADao<User> implements IUserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        return (User) getSession()
                .createQuery("FROM User WHERE login=:login")
                .setParameter("login", login)
                .uniqueResult();
    }
}
