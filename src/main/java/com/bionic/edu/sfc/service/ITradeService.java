package com.bionic.edu.sfc.service;

import com.bionic.edu.sfc.entity.FishItem;
import com.bionic.edu.sfc.exception.NotActualPriceException;
import com.bionic.edu.sfc.exception.NotActualWeightException;

import java.util.Collection;

/**
 * Created by docent on 04.12.14.
 */
public interface ITradeService {

    void registerNewBill(Collection<FishItem> fishItems) throws NotActualPriceException, NotActualWeightException;
}
