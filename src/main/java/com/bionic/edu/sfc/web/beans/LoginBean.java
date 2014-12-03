package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.ITradeService;
import com.bionic.edu.sfc.service.TradeServiceImpl;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

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
    private ITradeService tradeService;

    public String getUserName() {
        if (!isAuthenticated()) {
            return "";
        }
        return userService.getCurrentUser().getName();
    }

    public boolean isAuthenticated() {
        return userService.isAuthenticated();
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public String goToLogin() {
        return "/faces/login";
    }

    public boolean hasAccess(String userRole) {
        UserRole currentUserRole = userService.getCurrentUserRole();
        return currentUserRole != null && currentUserRole.toString().equals(userRole);
    }
}
