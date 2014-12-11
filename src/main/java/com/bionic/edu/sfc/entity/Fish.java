package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Ivan
 * 2014.09
 */
@Entity
@Table(indexes = {@Index(columnList = "name")})
public class Fish extends Hideable {

    @Column(length = 100,
            unique = true,
            nullable = false)
    @NotNull
    private String name;

    @Column(length = 100)
    private String logo;

    @Column
    @Type(type = "text")
    private String description;

    public Fish() { }

    public Fish(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fish fish = (Fish) o;

        return name.equals(fish.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Fish{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
