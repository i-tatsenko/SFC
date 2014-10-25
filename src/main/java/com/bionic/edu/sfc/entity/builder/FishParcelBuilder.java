package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
import com.bionic.edu.sfc.entity.Manufacturer;

import java.sql.Date;

/**
 * Ivan
 * 2014.10
 */
public class FishParcelBuilder {
    private Manufacturer manufacturer;
    private Fish fish;
    private FishShipSupply fishShipSupply;
    private String description;
    private double weight;
    private Date coldStoreRegistrationDate;
    private double weightSold;
    private double wholeSale;
    private double actualPrice;
    private boolean availableForCustomers;

    private FishParcelBuilder() {
    }

    public static FishParcelBuilder aFishParcel() {
        return new FishParcelBuilder();
    }

    public FishParcelBuilder withManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public FishParcelBuilder withFish(Fish fish) {
        this.fish = fish;
        return this;
    }

    public FishParcelBuilder withFishShipSupply(FishShipSupply fishShipSupply) {
        this.fishShipSupply = fishShipSupply;
        return this;
    }

    public FishParcelBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public FishParcelBuilder withWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public FishParcelBuilder withColdStoreRegistrationDate(Date coldStoreRegistrationDate) {
        this.coldStoreRegistrationDate = coldStoreRegistrationDate;
        return this;
    }

    public FishParcelBuilder withWeightSold(double weightSold) {
        this.weightSold = weightSold;
        return this;
    }

    public FishParcelBuilder withWholeSale(double wholeSale) {
        this.wholeSale = wholeSale;
        return this;
    }

    public FishParcelBuilder withActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public FishParcelBuilder withAvailableForCustomers(boolean availableForCustomers) {
        this.availableForCustomers = availableForCustomers;
        return this;
    }

    public FishParcel build() {
        FishParcel fishParcel = new FishParcel();
        fishParcel.setManufacturer(manufacturer);
        fishParcel.setFish(fish);
        fishParcel.setFishShipSupply(fishShipSupply);
        fishParcel.setDescription(description);
        fishParcel.setWeight(weight);
        fishParcel.setColdStoreRegistrationDate(coldStoreRegistrationDate);
        fishParcel.setWeightSold(weightSold);
        fishParcel.setWholeSale(wholeSale);
        fishParcel.setActualPrice(actualPrice);
        fishParcel.setAvailableForCustomers(availableForCustomers);
        return fishParcel;
    }
}
