package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Ivan
 * 2014.09
 */
@Entity
public class FishShipSupply extends Hideable {

    @Column(length = 50,
            unique = true,
            nullable = false)
    private String supplyCode;

    @Column(nullable = false)
    private Date creationDate;

    @Column
    @Type(type = "text")
    private String description;

    public FishShipSupply() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FishShipSupply that = (FishShipSupply) o;

        return supplyCode.equals(that.supplyCode);
    }

    @Override
    public int hashCode() {
        return supplyCode.hashCode();
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
