package com.bionic.edu.sfc.service.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.dao.IUserDao;
import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.service.dao.IUserService;
import com.bionic.edu.sfc.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Ivan
 * 2014.09
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class UserServiceImpl implements IUserService, UserDetailsService {

    public static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    public UserServiceImpl() { }

    @Override
    public final IDao<User> getDao() {
        return userDao;
    }

    @Override
    public void create(User user, String password) {
        user.setPasswordHash(Util.getPasswordHash(password));
        user.setCreationDate(new Date(System.currentTimeMillis()));
        userDao.create(user);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User authenticate(String login, String password) {
        LOGGER.info("Try auth with Login: " + login + " and password: ********");
        User user = findUserByLogin(login);
        if (user == null) {
            return null;
        }
        boolean authDataCorrect = Util.checkCredentials(password, user.getPasswordHash());
        return authDataCorrect ? user : null;
    }

    @Override
    public List<User> getAllSystemUsers() {
        return userDao.getAllSystemUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByLogin(username);
        return new SFCUserDetails(user);
    }

    private static class SFCUserDetails implements UserDetails {

        private User user;

        public SFCUserDetails(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getUserRole().toString());
            return Arrays.asList(sga);
        }

        @Override
        public String getPassword() {
            return user.getPasswordHash();
        }

        @Override
        public String getUsername() {
            return user.getLogin();
        }

        @Override
        public boolean isAccountNonExpired() {
            return user.isActive();
        }

        @Override
        public boolean isAccountNonLocked() {
            return user.isActive();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return user.isActive();
        }

    }
}
