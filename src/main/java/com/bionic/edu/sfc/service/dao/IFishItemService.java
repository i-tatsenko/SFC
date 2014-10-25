package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.FishItem;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishItemService extends IService<FishItem> {

    public List<FishItem> getReadyForWriteOff();

    public List<FishItem> getAllReadyForShipment();
}
