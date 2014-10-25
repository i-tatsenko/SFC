package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.entity.builder.UserBuilder;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by docent on 22.10.14.
 */
@Service
@Transactional(Transactional.TxType.REQUIRED)
public class BLService {

    @Autowired
    private IUserService userService;

    public void createDocentAccount() {
        User user = UserBuilder.anUser("docent")
                .withUserRole(UserRole.ROLE_SECURITY_OFFICER)
                .withName("Ivan")
                .build();
        user.setActive(true);
        userService.create(user, "31415Docent");
    }
}
