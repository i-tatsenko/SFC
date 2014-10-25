package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.Fish;

/**
 * Ivan
 * 2014.10
 */
public class FishBuilder {
    private String name;
    private String logo;
    private String description;

    private FishBuilder(String name) {
        this.name = name;
    }

    public static FishBuilder aFish(String name) {
        return new FishBuilder(name);
    }

    public FishBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FishBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public FishBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Fish build() {
        Fish fish = new Fish();
        fish.setName(name);
        fish.setLogo(logo);
        fish.setDescription(description);
        return fish;
    }
}
