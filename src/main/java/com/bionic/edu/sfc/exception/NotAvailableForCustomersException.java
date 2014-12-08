package com.bionic.edu.sfc.exception;

import com.bionic.edu.sfc.entity.FishItem;

import java.util.Collection;

/**
 * Created by docent on 08.12.14.
 */
public class NotAvailableForCustomersException extends Exception {

    private Collection<FishItem> fishItems;

    public NotAvailableForCustomersException(Collection<FishItem> fishItems) {
        this.fishItems = fishItems;
    }

    public NotAvailableForCustomersException(String message, Collection<FishItem> fishItems) {
        super(message);
        this.fishItems = fishItems;
    }

    public Collection<FishItem> getFishItems() {
        return fishItems;
    }
}
