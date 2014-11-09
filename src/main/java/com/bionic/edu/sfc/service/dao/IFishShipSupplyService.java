package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.entity.FishShipSupply;

import java.util.List;

/**
 * Ivan
 * 2014.10
 */
public interface IFishShipSupplyService extends IService<FishShipSupply> {

    public List<FishShipSupply> getAllTransportedToCM();
}
