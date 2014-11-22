package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.FishShipSupply;

import java.util.List;

/**
 * Ivan
 * 2014.09
 */
public interface IFishShipSupplyDao extends IDao<FishShipSupply> {

    List<FishShipSupply> getAllTransportedToCM();

    List<FishShipSupply> getAllReadyForTransport();

    List<FishShipSupply> getAllToRefund();
}
