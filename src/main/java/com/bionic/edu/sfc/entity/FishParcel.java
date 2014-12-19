package com.bionic.edu.sfc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Ivan
 * 2014.10
 */
@Entity
public class FishParcel extends Hideable {

    @ManyToOne(optional = false)
    @NotNull
    private Manufacturer manufacturer;

    @ManyToOne(optional = false)
    @NotNull
    private Fish fish;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private FishShipSupply fishShipSupply;

    @Type(type = "text")
    private String description;

    @Column(nullable = false)
    @Min(0)
    private double weight;

    private Date coldStoreRegistrationDate;

    @Min(0)
    private double weightSold;

    @Min(0)
    private double wholeSale;

    @Min(0)
    private double actualPrice;

    private boolean availableForCustomers;

    public FishParcel() { }

    @Override
    public String toString() {
        return "FishParcel{" +
                "id=" + getId() +
                ", manufacturer=" + manufacturer +
                ", fish=" + fish +
                ", fishShipSupply=" + fishShipSupply +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", coldStoreRegistrationDate=" + coldStoreRegistrationDate +
                ", weightSold=" + weightSold +
                ", wholeSale=" + wholeSale +
                ", actualPrice=" + actualPrice +
                ", availableForCustomers=" + availableForCustomers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FishParcel that = (FishParcel) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int i = 31 * (int) getId();
        i += (int)getId() >> 16;
        return i;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public FishShipSupply getFishShipSupply() {
        return fishShipSupply;
    }

    public void setFishShipSupply(FishShipSupply fishShipSupply) {
        this.fishShipSupply = fishShipSupply;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getColdStoreRegistrationDate() {
        return coldStoreRegistrationDate;
    }

    public void setColdStoreRegistrationDate(Date coldStoreRegistrationDate) {
        this.coldStoreRegistrationDate = coldStoreRegistrationDate;
    }

    public double getWeightSold() {
        return weightSold;
    }

    public void setWeightSold(double weightSold) {
        this.weightSold = weightSold;
    }

    public double getWholeSale() {
        return wholeSale;
    }

    public void setWholeSale(double wholeSale) {
        this.wholeSale = wholeSale;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public boolean isAvailableForCustomers() {
        return availableForCustomers;
    }

    public void setAvailableForCustomers(boolean isAvailableForCutomers) {
        this.availableForCustomers = isAvailableForCutomers;
    }
}
