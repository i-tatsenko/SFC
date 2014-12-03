package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.UserRole;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IUserService extends IService<User> {

    public void create(User user, String password);

    public User findUserByLogin(String login);

    public User authenticate(String login, String password);

    public List<User> getAllSystemUsers();

    public User getCurrentUser();

    public boolean isAuthenticated();

    public UserRole getCurrentUserRole();
}
