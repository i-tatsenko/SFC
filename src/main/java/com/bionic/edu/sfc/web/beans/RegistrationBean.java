package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.ICustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by docent on 23.10.14.
 */
@Named("regBean")
@Scope("request")
public class RegistrationBean {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationBean.class);
    @Autowired
    private ICustomerService customerService;

    private String name;

    private String login;

    private String password;

    private String previousUrl;

    public String doRegister() {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setLogin(login);
        customer.setUserRole(UserRole.ROLE_CUSTOMER);
        try {
            customerService.create(customer, password);
        } catch (Exception e) {
            return "/faces/register.xhtml?error=" + e.getMessage();
        }
        return "/faces/login.html";
    }

    public void doCancel(ActionEvent event) throws IOException {
        if (previousUrl != null) {
            LOGGER.info("Redirecting to " + previousUrl);
            FacesContext.getCurrentInstance().getExternalContext().redirect(previousUrl);
            return;
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public String goToRegistration() {
        previousUrl = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
        return "/faces/register";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }
}
