package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Ivan
 * 2014.09
 */
@Entity
@Table (name = "sfc_user",indexes = {@Index(columnList = "login")})
@Inheritance (strategy = InheritanceType.JOINED)
public class User extends Hideable {

    @Column(length = 100,
            nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private UserRole userRole;

    @Column(length = 50,
            nullable = false,
            unique = true)
    @NotNull
    private String login;

    @Column(length = 200,
            nullable = false)
    @NotNull
    private String passwordHash;

    @Type(type = "text")
    @Column
    private String description;

    @Column(nullable = false)
    private Date creationDate;

    private boolean active;

    public User() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", userRole=" + userRole +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", active=" + active +
                '}';
    }

    @Override
    public int hashCode() {
        return login.hashCode();
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
