package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IUserDao;
import com.bionic.edu.sfc.entity.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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
                .createQuery("FROM User WHERE login=:login " +
                        "AND active = true")
                .setParameter("login", login)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllSystemUsers() {
        return (List<User>)getSession()
                .createQuery("FROM User " +
                        "WHERE userRole != 'ROLE_CUSTOMER' ")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll(String orderValueName) {
        return (List<User>)getSession().createQuery("FROM User" +
                " WHERE active = true " +
                "ORDER BY :order")
                .setParameter("order", orderValueName)
                .list();
    }
}
