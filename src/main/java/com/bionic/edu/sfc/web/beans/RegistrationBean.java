package com.bionic.edu.sfc.web.beans;

import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.ICustomerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Created by docent on 23.10.14.
 */
@ManagedBean(name = "regBean")
@RequestScoped
public class RegistrationBean {

    private ICustomerService customerService;

    private String name;

    private String login;

    private String password;

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
        return "/faces/index.html";
    }

    public ICustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
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
}
