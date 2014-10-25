package com.bionic.edu.sfc.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

/**
 * Ivan
 * 2014.10
 */
@Entity
public class FishParcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private Fish fish;

    @ManyToOne
    private FishShipSupply fishShipSupply;

    @Type(type = "text")
    private String description;

    @Column(nullable = false)
    private double weight;

    private Date coldStoreRegistrationDate;

    private double weightSold;

    private double wholeSale;

    private double actualPrice;

    private boolean availableForCustomers;

    public FishParcel() { }

    @Override
    public String toString() {
        return "FishParcel{" +
                "id=" + id +
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

        if (!fish.equals(that.fish)) return false;
        if (!fishShipSupply.equals(that.fishShipSupply)) return false;
        if (!manufacturer.equals(that.manufacturer)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = manufacturer.hashCode();
        result = 31 * result + fish.hashCode();
        result = 31 * result + fishShipSupply.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
