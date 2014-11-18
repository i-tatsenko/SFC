package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.service.BLService;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by docent on 21.10.14.
 */
@Named
@Scope("session")
public class LoginBean {

    private static final Logger LOGGER = LogManager.getLogger(LoginBean.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private BLService blService;

    private String previousUrl;

    public String getUsername() {
        if (!isAuthenticated()) {
            return "";
        }
        String loginName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.findUserByLogin(loginName).getName();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public void showErrorIfAny() {
        if (isLoginError()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Bad credentials"));
        }
    }

    public boolean isLoginError() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("error") != null;
    }

    public String doLogin() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        RequestDispatcher dispatcher = request
                .getRequestDispatcher(request.getContextPath() + "/j_spring_security_check");

        dispatcher.forward(request,
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    public String doLogout() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.invalidateSession();
//        SecurityContextHolder.clearContext();

        HttpServletRequest request = (HttpServletRequest) context.getRequest();

        RequestDispatcher dispatcher = request
                .getRequestDispatcher(request.getContextPath() + "/j_spring_security_logout");

        dispatcher.forward(request,
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    public void doCancel(ActionEvent actionEvent) throws IOException {
        if (previousUrl != null) {
            LOGGER.info("Redirecting to " + previousUrl);
            FacesContext.getCurrentInstance().getExternalContext().redirect(previousUrl);
            return;
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public String goToLogin() {
        previousUrl = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
        return "/faces/login";
    }

    public boolean hasAccess(String userRole) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities == null) {
            return false;
        }
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(userRole)) {
                return true;
            }
        }
        return false;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }
}
