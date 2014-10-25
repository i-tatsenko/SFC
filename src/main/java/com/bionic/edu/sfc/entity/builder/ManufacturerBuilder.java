package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.Manufacturer;

/**
 * Ivan
 * 2014.10
 */
public class ManufacturerBuilder {
    private String name;
    private String logo;
    private String description;

    private ManufacturerBuilder(String name) {
        this.name = name;
    }

    public static ManufacturerBuilder aManufacturer(String name) {
        return new ManufacturerBuilder(name);
    }

    public ManufacturerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ManufacturerBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public ManufacturerBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Manufacturer build() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setLogo(logo);
        manufacturer.setDescription(description);
        return manufacturer;
    }
}
