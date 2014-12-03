package com.bionic.edu.sfc.exception;

import com.bionic.edu.sfc.entity.FishItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by docent on 04.12.14.
 */
public class NotActualWeightException extends Exception {

    private final Collection<FishItem> fishItems;

    public NotActualWeightException(Collection<FishItem> fishItems) {
        this.fishItems = new ArrayList<>(fishItems);
    }

    public Collection<FishItem> getFishItems() {
        return fishItems;
    }
}
