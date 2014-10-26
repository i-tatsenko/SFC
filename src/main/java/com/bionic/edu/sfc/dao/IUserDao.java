package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.User;

import java.util.List;


/**
 * Ivan
 * 2014.09
 */
public interface IUserDao extends IDao<User> {

    User findByLogin(String login);

    List<User> getAllSystemUsers();
}
