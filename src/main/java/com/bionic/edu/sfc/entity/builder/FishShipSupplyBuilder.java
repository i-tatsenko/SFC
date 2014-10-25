package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.FishShipSupply;

import java.sql.Date;
import java.util.Calendar;

/**
 * Ivan
 * 2014.10
 */
public class FishShipSupplyBuilder {
    private String supplyCode;
    private Date creationDate = new Date(Calendar.getInstance().getTimeInMillis());
    private String description;

    private FishShipSupplyBuilder(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public static FishShipSupplyBuilder aFishShipSupply(String supplyCode) {
        return new FishShipSupplyBuilder(supplyCode);
    }

    public FishShipSupplyBuilder withSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
        return this;
    }

    public FishShipSupplyBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public FishShipSupplyBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public FishShipSupply build() {
        FishShipSupply fishShipSupply = new FishShipSupply();
        fishShipSupply.setSupplyCode(supplyCode);
        fishShipSupply.setCreationDate(creationDate);
        fishShipSupply.setDescription(description);
        return fishShipSupply;
    }
}
