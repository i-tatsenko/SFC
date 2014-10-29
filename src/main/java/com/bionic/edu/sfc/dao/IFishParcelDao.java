package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishParcelDao extends IDao<FishParcel> {
    public List<FishParcel> getAllAvailableForCustomers();

    public List<FishParcel> getAllUnsaled();

    public List<FishParcel> getAllForFishSupply(FishShipSupply fishShipSupply);
}
