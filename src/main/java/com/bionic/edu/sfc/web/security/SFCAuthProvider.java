package com.bionic.edu.sfc.web.security;

import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by docent on 22.10.14.
 */
@Component
public class SFCAuthProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.info("authentication: " + authentication);
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        if (principal == null || credentials == null) {
            return authentication;
        }
        User user = userService.authenticate(principal.toString(), credentials.toString());
        if (user == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(principal, credentials, Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().toString())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
