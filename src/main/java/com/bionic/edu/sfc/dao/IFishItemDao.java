package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.Bill;
import com.bionic.edu.sfc.entity.FishItem;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishItemDao extends IDao<FishItem> {

    List<FishItem> getReadyForWriteOff();

    List<FishItem> getAllForBill(Bill bill);

    FishItem getForUuid(String uuid);
}
