package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Ivan
 * 2014.09
 */
@Entity
@Table(indexes = {@Index(columnList = "name")})
public class Fish {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 100,
            unique = true,
            nullable = false)
    private String name;

    @Column(length = 100)
    private String logo;

    @Column
    @Type(type = "text")
    private String description;

    public Fish() { }

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
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
