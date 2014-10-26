package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.service.BLService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by docent on 21.10.14.
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    private BLService blService;

    public BLService getBlService() {
        return blService;
    }

    public void setBlService(BLService blService) {
        this.blService = blService;
    }

    public String getUsername() {
        if (!isAuthenticated()) {
            return "";
        }
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
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
}
