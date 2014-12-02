package com.bionic.edu.sfc.entity.builder;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.Customer;
import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.entity.FishParcel;

import java.util.Date;
import java.util.UUID;

/**
 * Ivan
 * 2014.10
 */
public class FishItemBuilder {
    private long id;
    private String uuid = UUID.randomUUID().toString();
    private FishParcel fishParcel;
    private double price;
    private Customer customer;
    private Bill bill;
    private boolean removedFromColdStore;
    private boolean forWriteOff;
    private Date creationDate;
    private Date removedFromColdStoreDate;
    private Double weight;

    private FishItemBuilder() {
    }

    public static FishItemBuilder aFishItem() {
        return new FishItemBuilder();
    }

    public FishItemBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public FishItemBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public FishItemBuilder withFishParcel(FishParcel fishParcel) {
        this.fishParcel = fishParcel;
        return this;
    }

    public FishItemBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public FishItemBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public FishItemBuilder withBill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public FishItemBuilder withRemovedFromColdStore(boolean removedFromColdStore) {
        this.removedFromColdStore = removedFromColdStore;
        return this;
    }

    public FishItemBuilder withForWriteOff(boolean forWriteOff) {
        this.forWriteOff = forWriteOff;
        return this;
    }

    public FishItemBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public FishItemBuilder withRemovedFromColdStoreDate(Date removedFromColdStoreDate) {
        this.removedFromColdStoreDate = removedFromColdStoreDate;
        return this;
    }

    public FishItemBuilder withWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public FishItem build() {
        FishItem fishItem = new FishItem();
        fishItem.setId(id);
        fishItem.setUuid(uuid);
        fishItem.setFishParcel(fishParcel);
        fishItem.setPrice(price);
        fishItem.setCustomer(customer);
        fishItem.setBill(bill);
        fishItem.setRemovedFromColdStore(removedFromColdStore);
        fishItem.setForWriteOff(forWriteOff);
        fishItem.setCreationDate(creationDate);
        fishItem.setRemovedFromColdStoreDate(removedFromColdStoreDate);
        fishItem.setWeight(weight);
        return fishItem;
    }
}
