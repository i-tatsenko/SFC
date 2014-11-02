package com.bionic.edu.sfc.web.beans.secoff;

import com.bionic.edu.sfc.entity.User;
import com.bionic.edu.sfc.entity.UserRole;
import com.bionic.edu.sfc.service.dao.IUserService;
import org.apache.logging.log4j.LogManager;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.List;

/**
 * Created by docent on 26.10.14.
 */
@Named
@Scope("request")
public class UsersBean {

    private List<User> users;

    private User selectedUser;

    @Autowired
    private IUserService userService;

    private User newUser = new User();

    private String passForNewUser;

    public String getPassForNewUser() {
        return passForNewUser;
    }

    public void setPassForNewUser(String passForNewUser) {
        this.passForNewUser = passForNewUser;
    }

    @PostConstruct
    public void init() {
        users = userService.getAllSystemUsers();
    }

    public UserRole[] getUserRoles() {
        return UserRole.values();
    }

    public void onRowEdit(RowEditEvent event) {
        User editedUser = (User) event.getObject();
        LogManager.getLogger(UsersBean.class).info(editedUser);
        userService.update(editedUser);
    }

    public void onRowEditCancel(RowEditEvent event) {
        users = userService.getAllSystemUsers();
    }

    public void createNewUser(ActionEvent actionEvent) {
        try {
            userService.create(newUser, passForNewUser);
            newUser = new User();
            passForNewUser = null;
            users = userService.getAllSystemUsers();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("can't add user because of " + e.getMessage()));
        }
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}
