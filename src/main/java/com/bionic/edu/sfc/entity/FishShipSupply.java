package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FishShipSupplyStatus status;

    @OneToMany (fetch = FetchType.EAGER)
    private Set<FishParcel> fishParcels;

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

    public FishShipSupplyStatus getStatus() {
        return status;
    }

    public void setStatus(FishShipSupplyStatus status) {
        this.status = status;
    }

    public Set<FishParcel> getFishParcels() {
        return fishParcels;
    }

    public void setFishParcels(Set<FishParcel> fishParcels) {
        this.fishParcels = fishParcels;
    }
}
