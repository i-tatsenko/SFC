package com.bionic.edu.sfc.entity;

/**
 * Created by docent on 09.11.14.
 */
public enum FishShipSupplyStatus {
    READY_FOR_TRANSPORT("Ready for delivery to CS"),
    READY_FOR_COLD_STORE_REGISTRATION("Delivered to CS"),
    READY_FOR_SALE("Ready for sale"),
    REFUNDED("Refunded"),
    SOLD_OUT("Sold out");

    private String label;

    FishShipSupplyStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
